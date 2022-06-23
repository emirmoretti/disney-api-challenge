package com.alkemy.challenge.disneyapi.repo;

import com.alkemy.challenge.disneyapi.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonajeRepo extends JpaRepository<Personaje, Long> {

     List<Personaje> findByNombre(String nombre);
     List<Personaje> findAllByEdad(Integer edad);

    List<Personaje> findAllByPeliculasId(Long id);

    @Query("select p from Personaje p LEFT JOIN p.peliculas pe WHERE pe.id=?1")
    List<Personaje> findAllByPeliculaId(Long id);

}
