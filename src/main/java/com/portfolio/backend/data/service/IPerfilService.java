package com.portfolio.backend.data.service;

import com.portfolio.backend.data.model.Perfil;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

public interface IPerfilService {
    public Perfil getPerfil();
    void save(Perfil perfil);
    void save_nombre(String nombre);
    void save_descripcion(String descripcion);
    void save_fotoPerfil(String imagen);
}
