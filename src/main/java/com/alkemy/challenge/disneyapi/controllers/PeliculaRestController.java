package com.alkemy.challenge.disneyapi.controllers;

import com.alkemy.challenge.disneyapi.dto.PeliculaDTO;
import com.alkemy.challenge.disneyapi.entity.Pelicula;
import com.alkemy.challenge.disneyapi.entity.Personaje;
import com.alkemy.challenge.disneyapi.services.IUploadFileService;
import com.alkemy.challenge.disneyapi.services.impl.PeliculaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PeliculaRestController {

    @Autowired
    private PeliculaServiceImpl peliculaService;

    @Autowired
    private IUploadFileService uploadFileService;

    @GetMapping("/movies") /*Listado de peliculas*/
    public List<PeliculaDTO> findAll(){
        return peliculaService.findAll()
                .stream()
                .map(pelicula -> {
                    PeliculaDTO peliculaDto = new PeliculaDTO();
                    peliculaDto.setTitulo(pelicula.getTitulo());
                    peliculaDto.setImage(pelicula.getImage());
                    peliculaDto.setFecha(pelicula.getCreateAt());
                    return peliculaDto;
                }).collect(Collectors.toList());
    }

    @GetMapping("/movies/{id}") /*Detalle de la pelicula con todos sus campos*/
    public Pelicula findById(@PathVariable Long id){
        return peliculaService.findById(id);
    }

    /*Request params*/
    @RequestMapping(value = "/movies", params = "nombre")
    public List<Pelicula> findAllByName(@RequestParam String nombre){
        return peliculaService.findAllByName(nombre);
    }
    @RequestMapping(value = "/movies", params = "idGenero")
    public List<Pelicula> findAllByGen(@RequestParam Long idGenero){
        return peliculaService.findAllByGen(idGenero);
    }
    @RequestMapping(value = "/movies", params = "orden")
    public List<Pelicula> findAllByOrder(@RequestParam String orden) {
        return peliculaService.findAllByOrder(orden);
    }

    @PostMapping("/movies")
    public ResponseEntity<?> createMovie(@RequestBody Pelicula pelicula){
        peliculaService.save(pelicula);
        return new ResponseEntity<>("movie created", HttpStatus.CREATED);
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<?> editMovie(@PathVariable Long id, @RequestBody Pelicula pelicula){
        Pelicula peliculaDb = peliculaService.findById(id);
        peliculaDb.setTitulo(pelicula.getTitulo());
        peliculaDb.setImage(pelicula.getImage());
        peliculaDb.setCalificacion(pelicula.getCalificacion());
        peliculaDb.setCreateAt(pelicula.getCreateAt());
        peliculaService.save(peliculaDb);
        return new ResponseEntity<>("pelicula editada", HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Pelicula pelicula = peliculaService.findById(id);
        String nombreImage = pelicula.getImage();
        uploadFileService.eliminar(nombreImage);
        peliculaService.delete(id);
        return new ResponseEntity<>("Pelicula eliminada", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/movies/{peliculaId}/character")
    public ResponseEntity<?> addCharacter(@PathVariable Long peliculaId, @RequestBody Personaje personajeRequest){
        Pelicula pelicula = peliculaService.addPersonaje(peliculaId, personajeRequest);
        return new ResponseEntity<>(pelicula, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/movies/upload")
    public ResponseEntity<?> subirImg(
            @RequestParam(name = "archivo") MultipartFile archivo,
            @RequestParam(name = "id") Long id){
        Map<String, Object> response = new HashMap<>();
        Pelicula pelicula = peliculaService.findById(id);

        if(!archivo.isEmpty()){
            String nombreArchivo = null;
            try {
                nombreArchivo = uploadFileService.copiar(archivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen del cliente");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String nombreFotoAnterior = pelicula.getImage();
            uploadFileService.eliminar(nombreFotoAnterior);

            pelicula.setImage(nombreArchivo);
            peliculaService.save(pelicula);

            response.put("pelicula", pelicula);
            response.put("mensaje", "Imagen subida correctamente: " + nombreArchivo);
        }
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }
    @GetMapping("/movies/img/{nombreFoto:.+}")
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
