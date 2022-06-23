package com.alkemy.challenge.disneyapi.controllers;

import com.alkemy.challenge.disneyapi.dto.PersonajeDTO;
import com.alkemy.challenge.disneyapi.entity.Personaje;
import com.alkemy.challenge.disneyapi.services.impl.PersonajeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class PersonajeRestController {

    @Autowired
    private PersonajeServiceImpl personajeService;

    @GetMapping("/characters")
    public List<PersonajeDTO> findAll(){
        List<PersonajeDTO> personajes = personajeService.findAll()
                .stream()
                .map(personaje -> {
                    PersonajeDTO personajedto = new PersonajeDTO();
                    personajedto.setNombre(personaje.getNombre());
                    personajedto.setImagen(personaje.getImagen());
                    return personajedto;
                }).collect(Collectors.toList());
        return personajes;
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Personaje personaje = personajeService.findById(id);
        return new ResponseEntity<>(personaje, HttpStatus.OK);
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        personajeService.delete(id);
        return new ResponseEntity<>("cliente eliminado", HttpStatus.OK);
    }

}
