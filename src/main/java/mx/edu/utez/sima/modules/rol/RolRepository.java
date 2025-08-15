package mx.edu.utez.sima.modules.rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Rol findByName(String name);

    boolean existsByName(String name);

    List<Rol> findByNameContainingIgnoreCase(String name);

    @Query("SELECT r FROM Rol r WHERE SIZE(r.user) > 0")
    List<Rol> findByUserIsNotEmpty();

    @Query("SELECT r FROM Rol r WHERE SIZE(r.user) = 0")
    List<Rol> findByUserIsEmpty();

    @Query("SELECT COUNT(u) FROM BeanUser u WHERE u.rol.id = :rolId")
    Long countUsersByRolId(@Param("rolId") Long rolId);


    @Query("SELECT r FROM Rol r WHERE r.name IN :roleNames")
    List<Rol> findByNameIn(@Param("roleNames") List<String> roleNames);
}
