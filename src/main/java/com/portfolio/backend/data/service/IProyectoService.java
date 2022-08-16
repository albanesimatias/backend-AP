package com.portfolio.backend.data.service;

import com.portfolio.backend.data.model.Proyecto;

import java.util.List;
import java.util.Optional;

public interface IProyectoService {
    List<Proyecto> findAll();
    void save(Proyecto proyecto);
    boolean existsById(Long id);
    void deleteById(Long id);
    Optional<Proyecto> getById(Long id);
}
