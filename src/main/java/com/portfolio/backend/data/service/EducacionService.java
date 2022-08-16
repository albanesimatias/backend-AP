package com.portfolio.backend.data.service;

import com.portfolio.backend.data.model.Educacion;
import com.portfolio.backend.data.repository.IEducacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EducacionService implements  IEducacionService{
    @Autowired
    public IEducacionRepository educacionRepository;

    @Override
    public List<Educacion> findAll() {
        return educacionRepository.findAll();
    }

    @Override
    public void save(Educacion educacion) {
        educacionRepository.save(educacion);
    }

    @Override
    public boolean existsById(Long id) {
        return educacionRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        educacionRepository.deleteById(id);
    }

    @Override
    public Optional<Educacion> getById(Long id) {
        return educacionRepository.findById(id);
    }
}
