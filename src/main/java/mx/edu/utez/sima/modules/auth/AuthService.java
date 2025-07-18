package mx.edu.utez.sima.modules.auth;

import mx.edu.utez.sima.modules.user.BeanUser;
import mx.edu.utez.sima.security.CustomUserDetailsService;
import mx.edu.utez.sima.security.jwt.JwtService;
import mx.edu.utez.sima.utils.APIResponse;
import mx.edu.utez.sima.utils.PasswordEncoder;
import mx.edu.utez.sima.modules.auth.dto.LoginRequestDto;
import mx.edu.utez.sima.modules.user.UserRepository;
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
            BeanUser beanUser = userRepository.findByUsername(login.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!PasswordEncoder.verifyPassword(login.getPassword(), beanUser.getPassword())) {
                return new APIResponse("Usuario o contraseña incorrectos", true, HttpStatus.UNAUTHORIZED);
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(beanUser.getUsername());
            String token = jwtService.generateToken(userDetails);
            return new APIResponse("Inicio de sesión exitoso", token, false, HttpStatus.OK);
        } catch (Exception e) {
            return new APIResponse("Error en el inicio de sesión: " + e.getMessage(), true, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public APIResponse register(BeanUser beanUser) {
        try {
            if (userRepository.existsByUsername(beanUser.getUsername())) {
                return new APIResponse("El nombre de usuario ya está en uso", true, HttpStatus.CONFLICT);
            }
            
            if (userRepository.existsByEmail(beanUser.getEmail())) {
                return new APIResponse("El email ya está en uso", true, HttpStatus.CONFLICT);
            }

            if (beanUser.getRol() == null) {
                return new APIResponse("El rol es requerido", true, HttpStatus.BAD_REQUEST);
            }


            beanUser.setPassword(PasswordEncoder.encodePassword(beanUser.getPassword()));
            BeanUser savedBeanUser = userRepository.save(beanUser);
            
            BeanUser beanUserWithFullData = userRepository.findById(savedBeanUser.getId())
                    .orElse(savedBeanUser);
            
            return new APIResponse("Registro exitoso", beanUserWithFullData, false, HttpStatus.CREATED);
        } catch (Exception e) {
            return new APIResponse("Registro fallido: " + e.getMessage(), true, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}