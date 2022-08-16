package com.portfolio.backend.data.repository;

import com.portfolio.backend.data.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPerfilRepository extends JpaRepository<Perfil, Long> {
}
