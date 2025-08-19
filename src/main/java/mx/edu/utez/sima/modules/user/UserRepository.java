package mx.edu.utez.sima.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BeanUser, Long> {

    Optional<BeanUser> findByUuid(String uuid);

    Optional<BeanUser> findByUsername(String username);


    Optional<BeanUser> findByUuidAndRol_Name(String uuid, String rol_name);
    Optional<BeanUser> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<BeanUser> findByRolId(Long rolId);

    @Query("SELECT u FROM BeanUser u WHERE u.rol.name = :rolName")
    List<BeanUser> findByRolName(@Param("rolName") String rolName);

    List<BeanUser> findByActiveTrue();

    List<BeanUser> findByActiveFalse();

    List<BeanUser> findByActive(Boolean active);

    @Query("SELECT u FROM BeanUser u WHERE u.rol.name = :rolName AND u.storage IS NULL AND u.active = true")
    List<BeanUser> findByRolNameAndStorageIsNullAndActiveTrue(@Param("rolName") String rolName);

    @Query("SELECT u FROM BeanUser u WHERE u.storage IS NOT NULL")
    List<BeanUser> findByStorageIsNotNull();

    @Query("SELECT u FROM BeanUser u WHERE u.storage IS NULL")
    List<BeanUser> findByStorageIsNull();

    @Query("SELECT u FROM BeanUser u WHERE u.name LIKE %:name% OR u.lastName LIKE %:name%")
    List<BeanUser> findByNameContainingOrLastNameContaining(@Param("name") String name);

    List<BeanUser> findByRolIdAndActive(Long rolId, Boolean active);

    Long countByRolId(Long rolId);

    Long countByActiveTrue();

    @Query("SELECT u FROM BeanUser u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    List<BeanUser> findByCreatedAtBetween(@Param("startDate") java.time.LocalDateTime startDate,
                                          @Param("endDate") java.time.LocalDateTime endDate);
}
