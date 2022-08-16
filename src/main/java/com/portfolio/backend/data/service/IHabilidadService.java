package com.portfolio.backend.data.service;

import com.portfolio.backend.data.model.Habilidad;

import java.util.List;
import java.util.Optional;

public interface IHabilidadService {
    List<Habilidad> findAll();
    void save(Habilidad educacion);
    boolean existsById(Long id);
    void deleteById(Long id);
    Optional<Habilidad> getById(Long id);
}
