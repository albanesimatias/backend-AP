package com.portfolio.backend.data.service;
import com.portfolio.backend.data.model.Habilidad;
import com.portfolio.backend.data.repository.IHabilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HabilidadService implements IHabilidadService{
    @Autowired
    public IHabilidadRepository habilidadRepository;

    @Override
    public List<Habilidad> findAll() {
        return habilidadRepository.findAll();
    }

    @Override
    public void save(Habilidad habilidad) {
        habilidadRepository.save(habilidad);
    }

    @Override
    public boolean existsById(Long id) {
        return habilidadRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        habilidadRepository.deleteById(id);
    }

    @Override
    public Optional<Habilidad> getById(Long id) {
        return habilidadRepository.findById(id);
    }

}
