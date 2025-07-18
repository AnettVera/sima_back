package mx.edu.utez.sima.modules.auth.dto;

import mx.edu.utez.sima.modules.user.BeanUser;

import java.time.LocalDateTime;

public class UserResponseDto {
    private Long id;
    private String uuid;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private Boolean active;
    private LocalDateTime createdAt;
    private RoleResponseDto role;

    // Constructor vacío
    public UserResponseDto() {
    }

    // Constructor que convierte de BeanUser a DTO
    public UserResponseDto(BeanUser user) {
        this.id = user.getId();
        this.uuid = user.getUuid();
        this.username = user.getUsername();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.active = user.getActive();
        this.createdAt = user.getCreatedAt();
        if (user.getRol() != null) {
            this.role = new RoleResponseDto(user.getRol().getId(), user.getRol().getName());
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public RoleResponseDto getRole() {
        return role;
    }

    public void setRole(RoleResponseDto role) {
        this.role = role;
    }

    // Clase interna para el rol (sin referencias circulares)
    public static class RoleResponseDto {
        private Long id;
        private String name;

        public RoleResponseDto() {
        }

        public RoleResponseDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
} 