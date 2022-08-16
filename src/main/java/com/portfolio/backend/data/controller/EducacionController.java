package com.portfolio.backend.data.controller;
import com.portfolio.backend.data.model.Educacion;
import com.portfolio.backend.data.service.EducacionService;
import com.portfolio.backend.security.controller.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/educacion")
//@CrossOrigin("http://localhost:4200")
@CrossOrigin(origins = "https://frontend-portfolio-alba.web.app")
public class EducacionController {

    @Autowired
    private EducacionService educacionService;
    private static final String URL_REPO_EDU = "http://127.0.0.1:8887/educacion-imgs/";
    private static final String directorioImg = "src//main//resources//static//educacion-imgs";

    @GetMapping("/list")
    public ResponseEntity<List<Educacion>> list(){
        List<Educacion> educaciones = educacionService.findAll();
        educaciones.forEach( educacion -> {
                educacion.setImagen(URL_REPO_EDU+educacion.getImagen());
            }
        );
        return new ResponseEntity<>(educaciones, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile imagen, @RequestParam("educacion") String json) throws IOException {
        Educacion edu = new Gson().fromJson(json, Educacion.class);
        saveImg(imagen);
        edu.setImagen(imagen.getOriginalFilename());
        if(validarInfo(edu) == false)
            return new ResponseEntity<>(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);

        educacionService.save(edu);
        return new ResponseEntity<>(new Mensaje("Educacion agregada"), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestParam("id") String sId, @RequestParam("educacion") String json, @RequestParam("file") MultipartFile imagen) throws IOException {
        Long id = new Long(sId);
        Educacion edu = new Gson().fromJson(json, Educacion.class);
        edu.setId(id);
        if(!educacionService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe el id"), HttpStatus.BAD_REQUEST);
        if(saveImg(imagen))
            edu.setImagen(imagen.getOriginalFilename());
        else
            edu.setImagen(educacionService.getById(id).orElse(null).getImagen());
        if(validarInfo(edu) == false)
            return new ResponseEntity<>(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);
        educacionService.save(edu);
        return new ResponseEntity<>(new Mensaje("Informacion actualziada"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!educacionService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe el id"), HttpStatus.BAD_REQUEST);
        educacionService.deleteById(id);
        return new ResponseEntity<>(new Mensaje("Educacion borrada"),HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Educacion> getById(@PathVariable("id") Long id){
        if(!educacionService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Educacion educacion = educacionService.getById(id).orElse(null);
        return new ResponseEntity(educacion, HttpStatus.OK);
    }

    private boolean validarInfo(Educacion edu){
        if(StringUtils.isBlank(edu.getInstitucion()) ||
                StringUtils.isBlank(edu.getTitulo()) ||
                StringUtils.isBlank(edu.getDescripcion()))
            return false;
        return true;
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
}
