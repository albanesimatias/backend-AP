package com.portfolio.backend.data.service;

import com.portfolio.backend.data.model.Proyecto;
import com.portfolio.backend.data.repository.IProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProyectoService implements IProyectoService {
    @Autowired
    private IProyectoRepository proyectoRepository;

    @Override
    public List<Proyecto> findAll() {
        return proyectoRepository.findAll();
    }

    @Override
    public void save(Proyecto proyecto) {
        proyectoRepository.save(proyecto);
    }

    @Override
    public boolean existsById(Long id) {
        return proyectoRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        proyectoRepository.deleteById(id);
    }

    @Override
    public Optional<Proyecto> getById(Long id) {
        return proyectoRepository.findById(id);
    }
}
