package mx.edu.utez.sima.modules.storage;

import mx.edu.utez.sima.modules.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository< Storage, Long> {
    Optional<Storage> findByUuid(String uuid);

    Optional<Storage> findByStorageIdentifier(String storageIdentifier);

    boolean existsByStorageIdentifier(String storageIdentifier);

    List<Storage> findByStatus(Boolean status);

    List<Storage> findByStatusTrue();

    List<Storage> findByStatusFalse();

    List<Storage> findByCategoryId(Long categoryId);

    Optional<Storage> findByResponsibleId(Long responsibleId);

    List<Storage> findByResponsibleIsNull();

    List<Storage> findByResponsibleIsNotNull();

    List<Storage> findByStorageIdentifierContainingIgnoreCase(String identifier);

    List<Storage> findByCategoryIdAndStatus(Long categoryId, Boolean status);

    // Buscar almacenes que contengan un artículo específico
    @Query("SELECT s FROM Storage s JOIN s.articles a WHERE a.id = :articleId")
    List<Storage> findByArticlesId(@Param("articleId") Long articleId);

    @Query("SELECT s FROM Storage s WHERE s.id NOT IN (SELECT DISTINCT s2.id FROM Storage s2 JOIN s2.articles a WHERE a.id = :articleId)")
    List<Storage> findByArticlesIdNot(@Param("articleId") Long articleId);

    Long countByCategoryId(Long categoryId);

    Long countByStatusTrue();

    Long countByResponsibleIsNotNull();

    @Query("SELECT s FROM Storage s WHERE SIZE(s.articles) > 0")
    List<Storage> findByArticlesIsNotEmpty();

    @Query("SELECT s FROM Storage s WHERE SIZE(s.articles) = 0")
    List<Storage> findByArticlesIsEmpty();

    @Query("SELECT s FROM Storage s WHERE s.category.categoryName = :categoryName")
    List<Storage> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT s FROM Storage s WHERE s.responsible.name LIKE %:responsibleName% OR s.responsible.lastName LIKE %:responsibleName%")
    List<Storage> findByResponsibleNameContaining(@Param("responsibleName") String responsibleName);

    @Query("SELECT COUNT(a) FROM Storage s JOIN s.articles a WHERE s.id = :storageId")
    Long countArticlesByStorageId(@Param("storageId") Long storageId);

    List<Storage> findAllByOrderByStorageIdentifierAsc();

    @Query("SELECT s FROM Storage s WHERE s.category.id = :categoryId AND s.status = true")
    List<Storage> findByCategoryIdAndStatusTrue(@Param("categoryId") Long categoryId);

    Optional<Storage> findByCategory(Category techCategory);
}
