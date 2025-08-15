package mx.edu.utez.sima.modules.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository< Category, Long> {
    Optional<Category> findByUuid(String uuid);

    Optional<Category> findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);

    List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);

    @Query("SELECT c FROM Category c WHERE SIZE(c.storages) > 0")
    List<Category> findByStoragesIsNotEmpty();

    @Query("SELECT c FROM Category c WHERE SIZE(c.storages) = 0")
    List<Category> findByStoragesIsEmpty();

    @Query("SELECT c FROM Category c WHERE SIZE(c.articles) > 0")
    List<Category> findByArticlesIsNotEmpty();

    @Query("SELECT c FROM Category c WHERE SIZE(c.articles) = 0")
    List<Category> findByArticlesIsEmpty();

    @Query("SELECT COUNT(s) FROM Storage s WHERE s.category.id = :categoryId")
    Long countStoragesByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT COUNT(a) FROM Article a WHERE a.category.id = :categoryId")
    Long countArticlesByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT c FROM Category c WHERE c.createdAt BETWEEN :startDate AND :endDate")
    List<Category> findByCreatedAtBetween(@Param("startDate") java.time.LocalDateTime startDate,
                                          @Param("endDate") java.time.LocalDateTime endDate);

    @Query("SELECT DISTINCT c FROM Category c JOIN c.storages s WHERE s.status = true")
    List<Category> findByStoragesStatusTrue();
}
