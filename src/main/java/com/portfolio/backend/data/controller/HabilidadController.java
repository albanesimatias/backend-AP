package com.portfolio.backend.data.controller;
import com.portfolio.backend.data.model.Habilidad;
import com.portfolio.backend.data.service.HabilidadService;
import com.portfolio.backend.security.controller.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/habilidad")
//@CrossOrigin("http://localhost:4200")
@CrossOrigin(origins = "https://frontend-portfolio-alba.web.app")
public class HabilidadController {
    @Autowired
    private HabilidadService habilidadService;

    @GetMapping("/list")
    public ResponseEntity<List<Habilidad>> list(){
        List<Habilidad> habilidades = habilidadService.findAll();
        return new ResponseEntity<>(habilidades, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Habilidad habilidad) {
        if(validarInfo(habilidad) == false)
            return new ResponseEntity<>(new Mensaje("Los campos no pueden estar vacios"), HttpStatus.BAD_REQUEST);
        habilidadService.save(habilidad);
        return new ResponseEntity<>(new Mensaje("Habilidad agregada"), HttpStatus.CREATED);
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Habilidad habilidad) {
        if(!habilidadService.existsById(habilidad.getId()))
            return new ResponseEntity<>(new Mensaje("No existe el id"), HttpStatus.BAD_REQUEST);
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

    private boolean validarInfo(Habilidad habilidad){
        if(StringUtils.isBlank(habilidad.getNombre()) || StringUtils.isBlank(habilidad.getImagen()))
            return false;
        return true;
    }
}
