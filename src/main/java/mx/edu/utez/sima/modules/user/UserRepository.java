package mx.edu.utez.sima.modules.user;

import mx.edu.utez.sima.modules.rol.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    
    @Query("SELECT u FROM User u JOIN FETCH u.rol WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
    
    @Query("SELECT u FROM User u JOIN FETCH u.rol WHERE u.id = :id")
    Optional<User> findById(@Param("id") Long id);
    
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
