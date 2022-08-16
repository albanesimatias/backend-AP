package com.portfolio.backend.data.model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String imagen;
    private String githublink;

    public Proyecto() {
    }

    public Proyecto(Long id, String nombre, String imagen, String githublink) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.githublink = githublink;
    }
}
