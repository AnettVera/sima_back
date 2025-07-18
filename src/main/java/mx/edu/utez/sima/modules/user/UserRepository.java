package mx.edu.utez.sima.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BeanUser, Long> {
    Optional<BeanUser> findByUsernameAndPassword(String username, String password);
    
    @Query("SELECT u FROM BeanUser u JOIN FETCH u.rol WHERE u.username = :username")
    Optional<BeanUser> findByUsername(@Param("username") String username);
    
    @Query("SELECT u FROM BeanUser u JOIN FETCH u.rol WHERE u.id = :id")
    Optional<BeanUser> findById(@Param("id") Long id);
    
    Optional<BeanUser> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
