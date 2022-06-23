package com.alkemy.challenge.disneyapi.controllers;

import com.alkemy.challenge.disneyapi.dto.PersonajeDTO;
import com.alkemy.challenge.disneyapi.entity.Pelicula;
import com.alkemy.challenge.disneyapi.entity.Personaje;
import com.alkemy.challenge.disneyapi.repo.PersonajeRepo;
import com.alkemy.challenge.disneyapi.services.IPeliculaService;
import com.alkemy.challenge.disneyapi.services.impl.PersonajeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class PersonajeRestController {

    @Autowired
    private PersonajeServiceImpl personajeService;

    @Autowired
    private IPeliculaService peliculaService;

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

    /*Request mapping*/
    @RequestMapping(value = "/characters", params = "nombre")
    public  List<Personaje> findByName(@RequestParam String nombre){
        return personajeService.findByName(nombre);
    }
    @RequestMapping(value = "/characters", params = "edad")
    public List<Personaje> findByAge(@RequestParam Integer edad){
        return personajeService.findAllByAge(edad);
    }
    @RequestMapping(value = "/characters", params = "peliculaId")
    public List<Personaje> findByPelicula(@RequestParam Long peliculaId){
        return personajeService.findAllByPelicula(peliculaId);
    }

    @PostMapping("/characters")
    public ResponseEntity<?> create(@RequestBody Personaje personaje){
        Personaje personajeNuevo = personajeService.save(personaje);
        return new ResponseEntity<>(personajeNuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Personaje personaje = personajeService.findById(id);
        for (Pelicula pelicula : personaje.getPeliculas()){
            pelicula.getPersonajes().remove(personaje);
            peliculaService.save(pelicula);
        }
        personajeService.delete(id);
        return new ResponseEntity<>("cliente eliminado", HttpStatus.OK);
    }

}
