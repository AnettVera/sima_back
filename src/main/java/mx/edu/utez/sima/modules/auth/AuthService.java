package mx.edu.utez.sima.modules.auth;

import mx.edu.utez.sima.Security.CustomUserDetailsService;
import mx.edu.utez.sima.Security.JWT.JwtService;
import mx.edu.utez.sima.Util.APIResponse;
import mx.edu.utez.sima.Util.PasswordEncoder;
import mx.edu.utez.sima.modules.auth.dto.LoginRequestDto;
import mx.edu.utez.sima.modules.user.User;
import mx.edu.utez.sima.modules.user.UserRepository;
import mx.edu.utez.sima.modules.rol.RolRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private JwtService jwtService;
    private UserRepository userRepository;
    private CustomUserDetailsService customUserDetailsService;

    public AuthService(JwtService jwtService, UserRepository userRepository, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Transactional(readOnly = true)
    public APIResponse login(LoginRequestDto login) {
        try {
            User user = userRepository.findByUsername(login.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!PasswordEncoder.verifyPassword(login.getPassword(), user.getPassword())) {
                return new APIResponse("Usuario o contraseña incorrectos", true, HttpStatus.UNAUTHORIZED);
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtService.generateToken(userDetails);
            return new APIResponse("Inicio de sesión exitoso", token, false, HttpStatus.OK);
        } catch (Exception e) {
            return new APIResponse("Error en el inicio de sesión: " + e.getMessage(), true, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public APIResponse register(User user) {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                return new APIResponse("El nombre de usuario ya está en uso", true, HttpStatus.CONFLICT);
            }
            
            if (userRepository.existsByEmail(user.getEmail())) {
                return new APIResponse("El email ya está en uso", true, HttpStatus.CONFLICT);
            }

            if (user.getRol() == null) {
                return new APIResponse("El rol es requerido", true, HttpStatus.BAD_REQUEST);
            }


            user.setPassword(PasswordEncoder.encodePassword(user.getPassword()));
            User savedUser = userRepository.save(user);
            
            User userWithFullData = userRepository.findById(savedUser.getId())
                    .orElse(savedUser);
            
            return new APIResponse("Registro exitoso", userWithFullData, false, HttpStatus.CREATED);
        } catch (Exception e) {
            return new APIResponse("Registro fallido: " + e.getMessage(), true, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}