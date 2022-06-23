package com.alkemy.challenge.disneyapi.controllers;

import com.alkemy.challenge.disneyapi.dto.PeliculaDTO;
import com.alkemy.challenge.disneyapi.entity.Pelicula;
import com.alkemy.challenge.disneyapi.services.impl.PeliculaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PeliculaRestController {

    @Autowired
    private PeliculaServiceImpl peliculaService;

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

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        peliculaService.delete(id);
        return new ResponseEntity<>("Pelicula eliminada", HttpStatus.NO_CONTENT);
    }

}
