package com.portfolio.backend.data.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PaqueteDTO {
    private List<Educacion> educaciones;
    private List<Habilidad> habilidades;
    private List<Proyecto> proyectos;
    private Perfil perfil;

    public PaqueteDTO() {
    }

    public PaqueteDTO(List<Educacion> educaciones, List<Habilidad> habilidades, List<Proyecto> proyectos, Perfil perfil) {
        this.educaciones = educaciones;
        this.habilidades = habilidades;
        this.proyectos = proyectos;
        this.perfil = perfil;
    }
}
