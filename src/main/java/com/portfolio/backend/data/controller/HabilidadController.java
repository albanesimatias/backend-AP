package com.portfolio.backend.data.controller;
import com.google.gson.Gson;
import com.portfolio.backend.data.model.Habilidad;
import com.portfolio.backend.data.service.HabilidadService;
import com.portfolio.backend.security.controller.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/habilidad")
//@CrossOrigin("http://localhost:4200")
@CrossOrigin(origins = "https://frontend-portfolio-alba.web.app")
public class HabilidadController {
    @Autowired
    private HabilidadService habilidadService;
    private static final String URL_REPO_HAB = "http://127.0.0.1:8887/habilidades-imgs/";
    private static final String directorioImg = "src//main//resources//static//habilidades-imgs";

    @GetMapping("/list")
    public ResponseEntity<List<Habilidad>> list(){
        List<Habilidad> habilidades = habilidadService.findAll();
        habilidades.forEach( habilidad -> {
            habilidad.setImagen(URL_REPO_HAB+habilidad.getImagen());
        });
        return new ResponseEntity<>(habilidades, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestParam("habilidad") String json, @RequestParam("file") MultipartFile imagen) throws IOException {
        Habilidad habilidad = new Gson().fromJson(json, Habilidad.class);
        saveImg(imagen);
        habilidad.setImagen(imagen.getOriginalFilename());
        if(validarInfo(habilidad) == false)
            return new ResponseEntity<>(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);
        habilidadService.save(habilidad);
        return new ResponseEntity<>(new Mensaje("Habilidad agregada"), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestParam("id") String sId, @RequestParam("habilidad") String json, @RequestParam("file") MultipartFile imagen) throws IOException {
        Long id = new Long(sId);
        Habilidad habilidad = new Gson().fromJson(json, Habilidad.class);
        habilidad.setId(id);
        if(!habilidadService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe el id"), HttpStatus.BAD_REQUEST);
        if(saveImg(imagen))
            habilidad.setImagen(imagen.getOriginalFilename());
        else
            habilidad.setImagen(habilidadService.getById(id).orElse(null).getImagen());
        if(validarInfo(habilidad) == false)
            return new ResponseEntity<>(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);
        habilidadService.save(habilidad);
        return new ResponseEntity<>(new Mensaje("Informacion actualizada"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!habilidadService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe el id"), HttpStatus.BAD_REQUEST);
        habilidadService.deleteById(id);
        return new ResponseEntity<>(new Mensaje("Habilidad borrada"),HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Habilidad> getById(@PathVariable("id") Long id){
        if(!habilidadService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Habilidad habilidad = habilidadService.getById(id).orElse(null);
        return new ResponseEntity(habilidad, HttpStatus.OK);
    }

    private boolean saveImg(MultipartFile imagen) throws IOException {
        if(!imagen.isEmpty()){
            if(imagen.getOriginalFilename().equals("no-update-foto"))
                return false;
            Path directorioImagenes = Paths.get(directorioImg);
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            byte[] bytesImg = imagen.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
            Files.write(rutaCompleta,bytesImg);
            return true;
        }
        return false;
    }

    private boolean validarInfo(Habilidad habilidad){
        if(StringUtils.isBlank(habilidad.getNombre()))
            return false;
        return true;
    }
}
