package mx.edu.utez.sima.modules.auth;

import mx.edu.utez.sima.modules.auth.dto.RegisterRequestDto;
import mx.edu.utez.sima.modules.auth.dto.UserResponseDto;
import mx.edu.utez.sima.modules.rol.Rol;
import mx.edu.utez.sima.modules.rol.RolRepository;
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
    private RolRepository rolRepository;

    public AuthService(JwtService jwtService, UserRepository userRepository, CustomUserDetailsService customUserDetailsService, RolRepository rolRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.rolRepository = rolRepository;
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
    public APIResponse register(RegisterRequestDto registerDto) {
        try {
            if (userRepository.existsByUsername(registerDto.getUsername())) {
                return new APIResponse("El nombre de usuario ya está en uso", true, HttpStatus.CONFLICT);
            }

            if (userRepository.existsByEmail(registerDto.getEmail())) {
                return new APIResponse("El email ya está en uso", true, HttpStatus.CONFLICT);
            }

            Rol rol = rolRepository.findById(registerDto.getRoleId())
                    .orElse(null);

            if (rol == null) {
                return new APIResponse("El rol especificado no existe", true, HttpStatus.BAD_REQUEST);
            }

            BeanUser user = new BeanUser();
            user.setUsername(registerDto.getUsername());
            user.setPassword(PasswordEncoder.encodePassword(registerDto.getPassword()));
            user.setName(registerDto.getName());
            user.setLastName(registerDto.getLastName());
            user.setEmail(registerDto.getEmail());
            user.setRol(rol);
            user.setActive(true);

            BeanUser savedUser = userRepository.save(user);

            BeanUser userWithFullData = userRepository.findById(savedUser.getId())
                    .orElse(savedUser);

            UserResponseDto userResponse = new UserResponseDto(userWithFullData);

            return new APIResponse("Registro exitoso", userResponse, false, HttpStatus.CREATED);
        } catch (Exception e) {
            return new APIResponse("Registro fallido: " + e.getMessage(), true, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}