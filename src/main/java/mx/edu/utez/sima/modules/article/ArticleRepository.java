package mx.edu.utez.sima.modules.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByUuid(String uuid);

    List<Article> findByArticleNameContainingIgnoreCase(String articleName);

    Optional<Article> findByArticleName(String articleName);

    List<Article> findByCategoryId(Long categoryId);

    List<Article> findByQuantity(Long quantity);

    List<Article> findByQuantityLessThan(Long quantity);

    List<Article> findByQuantityGreaterThan(Long quantity);

    List<Article> findByQuantityBetween(Long minQuantity, Long maxQuantity);

    @Query("SELECT a FROM Article a JOIN a.storages s WHERE s.id = :storageId")
    List<Article> findByStoragesId(@Param("storageId") Long storageId);

    @Query("SELECT a FROM Article a WHERE SIZE(a.storages) = 0")
    List<Article> findByStoragesIsEmpty();

    @Query("SELECT a FROM Article a WHERE SIZE(a.storages) > 0")
    List<Article> findByStoragesIsNotEmpty();

    List<Article> findByArticleNameContainingIgnoreCaseAndCategoryId(String articleName, Long categoryId);

    List<Article> findByDescriptionContainingIgnoreCase(String description);

    Long countByCategoryId(Long categoryId);

    @Query("SELECT SUM(a.quantity) FROM Article a")
    Long sumAllQuantities();

    @Query("SELECT SUM(a.quantity) FROM Article a WHERE a.category.id = :categoryId")
    Long sumQuantityByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT a FROM Article a WHERE a.category.categoryName = :categoryName")
    List<Article> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT a FROM Article a WHERE a.quantity = 0")
    List<Article> findWithoutStock();

    @Query("SELECT a FROM Article a WHERE a.quantity < :threshold AND a.quantity > 0")
    List<Article> findWithLowStock(@Param("threshold") Long threshold);

    @Query("SELECT a FROM Article a WHERE a.quantity >= :threshold")
    List<Article> findWithSufficientStock(@Param("threshold") Long threshold);

    List<Article> findAllByOrderByArticleNameAsc();

    List<Article> findAllByOrderByQuantityDesc();

    @Query("SELECT DISTINCT a FROM Article a JOIN a.storages s WHERE s.id IN :storageIds")
    List<Article> findByStoragesIdIn(@Param("storageIds") List<Long> storageIds);

    @Query("SELECT a FROM Article a JOIN a.storages s WHERE a.category.id = :categoryId AND s.id = :storageId")
    List<Article> findByCategoryIdAndStoragesId(@Param("categoryId") Long categoryId, @Param("storageId") Long storageId);

    @Query("SELECT a FROM Article a WHERE a.id NOT IN (SELECT DISTINCT a2.id FROM Article a2 JOIN a2.storages s WHERE s.id = :storageId)")
    List<Article> findNotInStorage(@Param("storageId") Long storageId);

    @Query("SELECT COUNT(a) FROM Article a JOIN a.storages s WHERE s.id = :storageId")
    Long countByStoragesId(@Param("storageId") Long storageId);

    @Query("SELECT a FROM Article a WHERE a.quantity BETWEEN :minQuantity AND :maxQuantity AND a.category.id = :categoryId")
    List<Article> findByQuantityBetweenAndCategoryId(@Param("minQuantity") Long minQuantity,
                                                     @Param("maxQuantity") Long maxQuantity,
                                                     @Param("categoryId") Long categoryId);

    boolean existsByArticleName(String articleName);

    @Query("SELECT a FROM Article a WHERE a.category.id = :categoryId AND a.id NOT IN (SELECT DISTINCT a2.id FROM Article a2 JOIN a2.storages s WHERE s.id = :storageId)")
    List<Article> findAvailableForStorage(@Param("categoryId") Long categoryId, @Param("storageId") Long storageId);
}
