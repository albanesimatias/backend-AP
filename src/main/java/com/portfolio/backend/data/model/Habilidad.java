package com.portfolio.backend.data.model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Habilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int porcentaje;
    private String imagen;

    public Habilidad() {
    }

    public Habilidad(Long id, String nombre, int porcentaje, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.imagen = imagen;
    }
}
