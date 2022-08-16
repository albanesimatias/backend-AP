package com.portfolio.backend.data.controller;

import com.google.gson.Gson;
import com.portfolio.backend.data.model.Habilidad;
import com.portfolio.backend.data.model.Proyecto;
import com.portfolio.backend.data.service.ProyectoService;
import com.portfolio.backend.security.controller.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/proyecto")
//@CrossOrigin("http://localhost:4200")
@CrossOrigin(origins = "https://frontend-portfolio-alba.web.app")
public class ProyectoController {
    @Autowired
    private ProyectoService proyectoService;
    private static final String URL_REPO_PROYEC = "http://127.0.0.1:8887/proyectos-imgs/";
    private static final String directorioImg = "src//main//resources//static//proyectos-imgs";

    @GetMapping("/list")
    public ResponseEntity<List<Proyecto>> list(){
        List<Proyecto> proyectos = proyectoService.findAll();
        proyectos.forEach( proyecto -> {
            proyecto.setImagen(URL_REPO_PROYEC + proyecto.getImagen());
        });
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestParam("proyecto") String json, @RequestParam("file") MultipartFile imagen) throws IOException {
        Proyecto proyecto = new Gson().fromJson(json, Proyecto.class);
        if(validarInfo(proyecto) == false)
            return new ResponseEntity<>(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);
        saveImg(imagen);
        proyecto.setImagen(imagen.getOriginalFilename());
        proyectoService.save(proyecto);
        return new ResponseEntity<>(new Mensaje("Proyecto agregado"), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestParam("id") String sId, @RequestParam("proyecto") String json, @RequestParam("file") MultipartFile imagen) throws IOException {
        Long id = new Long(sId);
        Proyecto proyecto = new Gson().fromJson(json, Proyecto.class);
        proyecto.setId(id);
        if(!proyectoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe el id"), HttpStatus.BAD_REQUEST);
        if(saveImg(imagen))
            proyecto.setImagen(imagen.getOriginalFilename());
        else
            proyecto.setImagen(proyectoService.getById(id).orElse(null).getImagen());
        if(validarInfo(proyecto) == false)
            return new ResponseEntity<>(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);
        proyectoService.save(proyecto);
        return new ResponseEntity<>(new Mensaje("Informacion actualizada"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!proyectoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe el id"), HttpStatus.BAD_REQUEST);
        proyectoService.deleteById(id);
        return new ResponseEntity<>(new Mensaje("Proyecto borrada"),HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Habilidad> getById(@PathVariable("id") Long id){
        if(!proyectoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Proyecto proyecto = proyectoService.getById(id).orElse(null);
        return new ResponseEntity(proyecto, HttpStatus.OK);
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

    private boolean validarInfo(Proyecto proyecto){
        if(StringUtils.isBlank(proyecto.getNombre()))
            return false;
        return true;
    }

}
