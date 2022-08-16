package com.portfolio.backend.security.entity;

import com.portfolio.backend.security.enums.RolNombre;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RolNombre rolNombre;

    public Rol(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Rol() {
    }
}
