package com.portfolio.backend.data.service;
import com.portfolio.backend.data.model.Educacion;
import java.util.List;
import java.util.Optional;

public interface IEducacionService  {
    List<Educacion> findAll();
    void save(Educacion educacion);
    boolean existsById(Long id);
    void deleteById(Long id);
    Optional<Educacion> getById(Long id);
}
