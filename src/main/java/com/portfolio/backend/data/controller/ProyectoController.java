package com.portfolio.backend.data.controller;
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
import java.util.List;

@RestController
@RequestMapping("/proyecto")
@CrossOrigin("http://localhost:4200")
//@CrossOrigin(origins = "https://frontend-portfolio-alba.web.app")
public class ProyectoController {
    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/list")
    public ResponseEntity<List<Proyecto>> list(){
        List<Proyecto> proyectos = proyectoService.findAll();
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Proyecto proyecto) {
        if(validarInfo(proyecto) == false)
            return new ResponseEntity<>(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);
        proyectoService.save(proyecto);
        return new ResponseEntity<>(new Mensaje("Proyecto agregado"), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Proyecto proyecto) {
        if(!proyectoService.existsById(proyecto.getId()))
            return new ResponseEntity<>(new Mensaje("No existe el id"), HttpStatus.BAD_REQUEST);
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

    private boolean validarInfo(Proyecto proyecto){
        if(StringUtils.isBlank(proyecto.getNombre()) || StringUtils.isBlank(proyecto.getImagen()) || StringUtils.isBlank(proyecto.getGithublink()))
            return false;
        return true;
    }

}
