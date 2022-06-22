package com.alkemy.challenge.disneyapi.services.impl;

import com.alkemy.challenge.disneyapi.entity.Pelicula;
import com.alkemy.challenge.disneyapi.repo.PeliculaRepo;
import com.alkemy.challenge.disneyapi.services.IPeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeliculaServiceImpl implements IPeliculaService {

    @Autowired
    private PeliculaRepo peliculaRepo;

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
}
