package com.portfolio.backend.data.service;

import com.portfolio.backend.data.model.Perfil;
import com.portfolio.backend.data.repository.IPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Optional;

@Service
@Transactional
public class PerfilService implements IPerfilService{
    @Autowired
    private IPerfilRepository perfilRepository;

    @Override
    public Perfil getPerfil() {
        Long id = 1L;
        Perfil perfil = perfilRepository.findById(id).orElse(null);
        if(perfil == null){
            perfil = new Perfil(1L,"","",null);
        }
        return perfil;
    }

    @Override
    public void save(Perfil perfil) {
        perfil.setId(1L);
        perfilRepository.save(perfil);
    }

    @Override
    public void save_nombre(String nombre) {
        Perfil perfil = perfilRepository.findById(1L).orElse(null);
        if(perfil == null){
            perfil = new Perfil(1L,"","",null);
        }
        perfil.setNombre(nombre);
        perfilRepository.save(perfil);
    }

    @Override
    public void save_descripcion(String descripcion) {
        Perfil perfil = perfilRepository.findById(1L).orElse(null);
        if(perfil == null){
            perfil = new Perfil(1L,"","",null);
        }
        perfil.setDescripcion(descripcion);
        perfilRepository.save(perfil);
    }

    @Override
    public void save_fotoPerfil(String imagen) {
        Perfil perfil = perfilRepository.findById(1L).orElse(null);
        if(perfil == null) {
            perfil = new Perfil(1L, "", "", "");
        }
        perfil.setImagen(imagen);
        perfilRepository.save(perfil);
    }
}
