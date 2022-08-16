package com.portfolio.backend.data.repository;

import com.portfolio.backend.data.model.Habilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHabilidadRepository extends JpaRepository<Habilidad, Long> {
}
