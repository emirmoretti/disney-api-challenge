package com.alkemy.challenge.disneyapi.services;

import com.alkemy.challenge.disneyapi.entity.Personaje;

import java.util.List;

public interface IPersonajeService {

    public Personaje findById(Long id);

    public List<Personaje> findAll();

    public Personaje save(Personaje personaje);

    public void delete(Long id);

}
