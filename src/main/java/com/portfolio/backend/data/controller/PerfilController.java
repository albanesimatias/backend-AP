package com.portfolio.backend.data.controller;
import com.portfolio.backend.data.model.Perfil;
import com.portfolio.backend.data.service.PerfilService;
import com.portfolio.backend.security.controller.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.*;

@RestController
@RequestMapping("/perfil/")
//@CrossOrigin("http://localhost:4200")
@CrossOrigin(origins = "https://frontend-portfolio-alba.web.app")
public class PerfilController {
    @Autowired
    private PerfilService perfilService;
    @GetMapping("get")
    public ResponseEntity<Perfil> list() throws FileNotFoundException {
        Perfil perfil = perfilService.getPerfil();
        return new ResponseEntity<>(perfil, HttpStatus.OK);
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Perfil perfil){
        perfilService.save(perfil);
        return new ResponseEntity<>(new Mensaje("Perfil actualizado"), HttpStatus.OK);
    }

    @PutMapping("update-nombre")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update_nombre(@RequestBody String nombre){
        perfilService.save_nombre(nombre);
        return new ResponseEntity<>(new Mensaje("Nombre actualizado"), HttpStatus.OK);
    }

    @PutMapping("update-descripcion")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update_descripcion(@RequestBody String descripcion){
        perfilService.save_descripcion(descripcion);
        return new ResponseEntity<>(new Mensaje("Descripcion actualizada"), HttpStatus.OK);
    }

    @PostMapping("subir-foto")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update_foto(@RequestBody String url) {
        if(StringUtils.isBlank(url))
            return new ResponseEntity<>(new Mensaje("URL INVALIDO"), HttpStatus.BAD_REQUEST);
        perfilService.save_fotoPerfil(url);
        return new ResponseEntity<>(new Mensaje("Foto actualizada"), HttpStatus.OK);
    }

}
