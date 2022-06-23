package com.alkemy.challenge.disneyapi.services.impl;

import com.alkemy.challenge.disneyapi.entity.Personaje;
import com.alkemy.challenge.disneyapi.repo.PersonajeRepo;
import com.alkemy.challenge.disneyapi.services.IPersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonajeServiceImpl implements IPersonajeService {

    @Autowired
    PersonajeRepo personajeRepo;

    @Transactional(readOnly = true)
    public  List<Personaje> findByName(String nombre){
        return personajeRepo.findByNombre(nombre);
    }
    @Transactional(readOnly = true)
    public List<Personaje> findAllByAge(Integer edad){
        return personajeRepo.findAllByEdad(edad);
    }
    @Transactional(readOnly = true)
    public List<Personaje> findAllByPelicula(Long id){
        return personajeRepo.findAllByPeliculaId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Personaje findById(Long id) {
        return personajeRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> findAll() {
        return personajeRepo.findAll();
    }

    @Override
    @Transactional
    public Personaje save(Personaje personaje) {
        return personajeRepo.save(personaje);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        personajeRepo.deleteById(id);
    }
}
