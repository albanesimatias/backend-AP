package com.portfolio.backend.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Educacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String institucion;
    private String titulo;
    private String descripcion;
    private String imagen;

    public Educacion() {
    }

    public Educacion(Long id, String institucion, String titulo, String descripcion, String imagen) {
        this.institucion = institucion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.id = id;
    }
}
