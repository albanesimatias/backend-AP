package com.portfolio.backend.data.controller;
import com.portfolio.backend.data.model.PaqueteDTO;
import com.portfolio.backend.data.service.EducacionService;
import com.portfolio.backend.data.service.HabilidadService;
import com.portfolio.backend.data.service.PerfilService;
import com.portfolio.backend.data.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paquete")
//@CrossOrigin("http://localhost:4200")
@CrossOrigin(origins = "https://frontend-portfolio-alba.web.app")
public class PaqueteController {
    @Autowired
    private EducacionService educacionService;
    @Autowired
    private HabilidadService habilidadService;
    @Autowired
    private ProyectoService proyectoService;
    @Autowired
    private PerfilService perfilService;

    @GetMapping("/get")
    public ResponseEntity<PaqueteDTO> list(){
        PaqueteDTO paquete = new PaqueteDTO();
        paquete.setEducaciones(educacionService.findAll());
        paquete.setHabilidades(habilidadService.findAll());
        paquete.setProyectos(proyectoService.findAll());
        paquete.setPerfil(perfilService.getPerfil());
        return new ResponseEntity<>(paquete, HttpStatus.OK);
    }
}
