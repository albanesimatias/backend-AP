package com.portfolio.backend.data.controller;
import com.portfolio.backend.data.model.Perfil;
import com.portfolio.backend.data.service.PerfilService;
import com.portfolio.backend.security.controller.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/perfil/")
//@CrossOrigin("http://localhost:4200")
@CrossOrigin(origins = "https://frontend-portfolio-alba.web.app")
public class PerfilController {
    @Autowired
    private PerfilService perfilService;
    private static final String URL_REPO_PERFIL = "http://127.0.0.1:8887/perfil-imgs/";
    private static final String directorioImgs = "src//main//resources//static//perfil-imgs";
    @GetMapping("get")
    public ResponseEntity<Perfil> list() throws FileNotFoundException {
        Perfil perfil = perfilService.getPerfil();
        Path path = Paths.get("src//main//resources//static//img//"+perfil.getImagen());
        String imgpath = path.toFile().getAbsolutePath();
        perfil.setImagen(imgpath+perfil.getImagen());
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
    public ResponseEntity<?> update_foto(@RequestParam("file") MultipartFile imagen) throws IOException {
        if(!imagen.isEmpty()){
            Path directorioImagenes = Paths.get(directorioImgs);
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            byte[] bytesImg = imagen.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
            new File(directorioImagenes+"//"+perfilService.getPerfil().getImagen()).delete();
            Files.write(rutaCompleta,bytesImg);
            perfilService.save_fotoPerfil(imagen.getOriginalFilename());
        }
        return new ResponseEntity<>(new Mensaje("Foto actualizada"), HttpStatus.OK);
    }

}
