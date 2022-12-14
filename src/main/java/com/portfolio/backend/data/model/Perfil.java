package com.portfolio.backend.data.model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Perfil {
    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;

    public Perfil() {
    }

    public Perfil(Long id, String nombre, String descripcion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }
}
