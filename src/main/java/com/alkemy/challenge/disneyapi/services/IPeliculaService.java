package com.alkemy.challenge.disneyapi.services;

import com.alkemy.challenge.disneyapi.entity.Pelicula;

import java.util.List;

public interface IPeliculaService {

    public Pelicula findById(Long id);

    public List<Pelicula> findAll();

    public Pelicula save(Pelicula pelicula);

    public void delete(Long id);

}
