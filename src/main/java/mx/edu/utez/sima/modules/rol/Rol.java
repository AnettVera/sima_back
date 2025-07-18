package mx.edu.utez.sima.modules.rol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import mx.edu.utez.sima.modules.user.BeanUser;

import java.util.List;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "rol")
    @JsonIgnore
    private List<BeanUser> beanUsers;

    public Rol() {
    }

    public Rol(Long id, String name, List<BeanUser> beanUser) {
        this.id = id;
        this.name = name;
        this.beanUsers = beanUser;
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

    public List<BeanUser> getUser() {
        return beanUsers;
    }

    public void setUser(List<BeanUser> beanUser) {
        this.beanUsers = beanUser;
    }
}
