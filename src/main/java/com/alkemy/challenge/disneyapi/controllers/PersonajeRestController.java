package com.alkemy.challenge.disneyapi.controllers;

import com.alkemy.challenge.disneyapi.dto.PersonajeDTO;
import com.alkemy.challenge.disneyapi.entity.Pelicula;
import com.alkemy.challenge.disneyapi.entity.Personaje;
import com.alkemy.challenge.disneyapi.repo.PersonajeRepo;
import com.alkemy.challenge.disneyapi.services.IPeliculaService;
import com.alkemy.challenge.disneyapi.services.IUploadFileService;
import com.alkemy.challenge.disneyapi.services.impl.PeliculaServiceImpl;
import com.alkemy.challenge.disneyapi.services.impl.PersonajeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private IUploadFileService uploadFileService;

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
        String nombreFotoAnterior = personaje.getImagen();
        uploadFileService.eliminar(nombreFotoAnterior);
        personajeService.delete(id);
        return new ResponseEntity<>("cliente eliminado", HttpStatus.OK);
    }

    @PostMapping("/characters/upload")
    public ResponseEntity<?> subirImg(
            @RequestParam(name = "archivo") MultipartFile archivo,
            @RequestParam(name = "id") Long id){
        Map<String, Object> response = new HashMap<>();
        Personaje personaje = personajeService.findById(id);

        if(!archivo.isEmpty()){
            String nombreArchivo = null;
            try {
                nombreArchivo = uploadFileService.copiar(archivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen del cliente");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String nombreFotoAnterior = personaje.getImagen();
            uploadFileService.eliminar(nombreFotoAnterior);

            personaje.setImagen(nombreArchivo);
            personajeService.save(personaje);

            response.put("personaje", personaje);
            response.put("mensaje", "Imagen subida correctamente: " + nombreArchivo);
        }
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }

    @GetMapping("/characters/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
        Resource recurso = null;
        try{
            recurso = uploadFileService.cargar(nombreFoto);
        } catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
        return new ResponseEntity<>(recurso,cabecera,HttpStatus.OK);
    }
}
