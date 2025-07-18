package mx.edu.utez.sima.security;

import mx.edu.utez.sima.modules.user.BeanUser;
import mx.edu.utez.sima.modules.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar primero al usuario
        BeanUser found = userRepository.findByUsername(username).orElse(null);
        if (found == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Generar las autoridades para el contexto de seguridad
        // Autoridades = Roles
        // authority = ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_DEV -> filterChain
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + found.getRol().getName());

        // Retornar el objeto de usuario para registrar en el contexto de seguridad
        return new User(
                found.getUsername(),
                found.getPassword(),
                Collections.singleton(authority)
        );
    }
}
