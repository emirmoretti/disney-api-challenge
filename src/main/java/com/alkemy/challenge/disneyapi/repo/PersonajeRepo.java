package com.alkemy.challenge.disneyapi.repo;

import com.alkemy.challenge.disneyapi.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonajeRepo extends JpaRepository<Personaje, Long> {

    public Personaje findByNombre(String nombre);

    public Personaje findByEdad(Integer edad);

    // public Personaje findByPelicula(Long id);

}
