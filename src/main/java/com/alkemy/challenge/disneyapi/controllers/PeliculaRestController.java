package com.alkemy.challenge.disneyapi.controllers;

import com.alkemy.challenge.disneyapi.entity.Pelicula;
import com.alkemy.challenge.disneyapi.services.impl.PeliculaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PeliculaRestController {

    @Autowired
    private PeliculaServiceImpl peliculaService;

    @GetMapping("/peliculas")
    public List<Pelicula> findAll(){
        return peliculaService.findAll();
    }

}
