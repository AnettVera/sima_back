package mx.edu.utez.sima.modules.article;

import jakarta.validation.constraints.*;
import mx.edu.utez.sima.modules.category.Category;
public class ArticleDTO {

    private Long id;

    @NotBlank(message = "El nombre del artículo es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre del artículo debe tener entre 3 y 50 caracteres")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$",
            message = "El nombre del artículo solo puede contener letras, espacios y acentos"
    )
    private String articleName;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 50, message = "La descripción debe tener entre 10 y 50 caracteres")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9.,;:()\"'\\s-]+$",
            message = "La descripción solo puede contener letras, números, acentos, espacios y puntuación básica"
    )
    private String description;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Digits(integer = 10, fraction = 0, message = "La cantidad debe tener hasta 10 dígitos sin decimales")
    private Long quantity;


    private Long category;

    public Article toEntity() {
        Article article = new Article();
        article.setArticleName(this.articleName);
        article.setDescription(this.description);
        article.setQuantity(this.quantity);
        Category category = new Category();
        category.setId(this.category);
        article.setCategory(category);
        return article;
    }
    public Article toEntityUpdate() {
        Article article = new Article();
        article.setId(this.id);
        article.setArticleName(this.articleName);
        article.setDescription(this.description);
        article.setQuantity(this.quantity);
        Category category = new Category();
        category.setId(this.category);
        article.setCategory(category);
        return article;
    }

    public ArticleDTO(Long id, String articleName, String description, Long quantity, Long category) {
        this.id = id;
        this.articleName = articleName;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
