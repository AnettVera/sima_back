package mx.edu.utez.sima.modules.user;


import jakarta.validation.constraints.*;

public class UserDTO {
    private Long id;
    private String username;

    @NotNull(message = "Favor de ingresar el nombre")
    @NotBlank(message = "Favor de no dejar el nombre en blanco")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚÑñ\\s]+$",
            message = "El nombre solo puede contener letras, espacios y acentos"
    )
    private String name;

    @NotNull(message = "Favor de ingresar el apellido")
    @NotBlank(message = "Favor de no dejar el apellido en blanco")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚÑñ\\s]+$",
            message = "El apellido solo puede contener letras, espacios y acentos"
    )
    private String lastName;

    @NotNull(message = "Favor de ingresar el correo")
    @NotBlank(message = "Favor de no dejar el correo en blanco")
    @Size(max = 100, message = "El correo no puede exceder los 100 caracteres")
    @Email(message = "Ingrese un correo válido")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Ingrese un correo válido"
    )
    private String email;


    public BeanUser toEntity() {
        BeanUser user = new BeanUser();
        user.setName(this.name);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setActive(true);
        user.setTemporal_password(false);

        return user;
    }

    public BeanUser toEntityUpdate() {
        BeanUser user = new BeanUser();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setName(this.name);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setActive(true);
        user.setTemporal_password(false);

        return user;
    }

    public UserDTO(Long id, String username,  String name, String lastName, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
