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
import java.util.List;

@RestController
@RequestMapping("/educacion")
//@CrossOrigin("http://localhost:4200")
@CrossOrigin(origins = "https://frontend-portfolio-alba.web.app")
public class EducacionController {

    @Autowired
    private EducacionService educacionService;

    @GetMapping("/list")
    public ResponseEntity<List<Educacion>> list(){
        List<Educacion> educaciones = educacionService.findAll();
        return new ResponseEntity<>(educaciones, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Educacion educacion) {
        if(validarInfo(educacion) == false)
            return new ResponseEntity<>(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);
        educacionService.save(educacion);
        return new ResponseEntity<>(new Mensaje("Educacion agregada"), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Educacion educacion) {
        if(!educacionService.existsById(educacion.getId()))
            return new ResponseEntity<>(new Mensaje("No existe el id"), HttpStatus.BAD_REQUEST);
        if(validarInfo(educacion) == false)
            return new ResponseEntity<>(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);
        educacionService.save(educacion);
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
}
