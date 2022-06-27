package com.alkemy.challenge.disneyapi.services.impl;

import com.alkemy.challenge.disneyapi.entity.Pelicula;
import com.alkemy.challenge.disneyapi.entity.Personaje;
import com.alkemy.challenge.disneyapi.repo.PeliculaRepo;
import com.alkemy.challenge.disneyapi.repo.PersonajeRepo;
import com.alkemy.challenge.disneyapi.services.IPeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class PeliculaServiceImpl implements IPeliculaService {

    @Autowired
    private PeliculaRepo peliculaRepo;
    @Autowired
    private PersonajeRepo personajeRepo;


    /*request movies*/
    @Transactional(readOnly = true)
    public List<Pelicula> findAllByName(String nombre){
       return peliculaRepo.findAllByTitulo(nombre);
    }

    @Transactional(readOnly = true)
    public List<Pelicula> findAllByGen(Long idGenero){
        return peliculaRepo.findAllByGeneroId(idGenero);
    }

    @Transactional(readOnly = true)
    public List<Pelicula> findAllByOrder(String orden){
        if(orden.equalsIgnoreCase("asc")) {
            return peliculaRepo.findAllByOrderByCreateAtAsc();
        } else if (orden.equalsIgnoreCase("desc")){
            return peliculaRepo.findAllByOrderByCreateAtDesc();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Pelicula findById(Long id) {
        return peliculaRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula> findAll() {
        return peliculaRepo.findAll();
    }

    @Override
    @Transactional
    public Pelicula save(Pelicula pelicula) {
        return peliculaRepo.save(pelicula);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        peliculaRepo.deleteById(id);
    }
    @Transactional
    public Pelicula addPersonaje(Long peliculaId, Personaje personajeRequest){
        Pelicula pelicula = peliculaRepo.findById(peliculaId).orElseThrow(() -> new RuntimeException("No existe el id Pelicula"));
        Long personajeId = personajeRequest.getId();
        Personaje personaje = null;
        if(personajeId != null){
            personaje = personajeRepo.findById(personajeRequest.getId()).orElseThrow(() -> new RuntimeException("No existe personaje"));
        }
        // si personaje existe
        if (personaje != null){
            for (Personaje personajeUnit : pelicula.getPersonajes()) {
                if (personajeUnit.equals(personaje)){
                    throw new RuntimeException("Personaje ya existe en la pelicula");
                }
            }
            Set<Personaje> personajes = pelicula.getPersonajes();
            personajes.add(personaje);
            pelicula.setPersonajes(personajes);
            peliculaRepo.save(pelicula);
            return pelicula;
        }
        Set<Personaje> personajes = pelicula.getPersonajes();
        personajes.add(personajeRequest);
        pelicula.setPersonajes(personajes);
        personajeRepo.save(personajeRequest);
        peliculaRepo.save(pelicula);
        return pelicula;
    }
}
