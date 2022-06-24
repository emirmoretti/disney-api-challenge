package com.alkemy.challenge.disneyapi.repo;

import com.alkemy.challenge.disneyapi.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepo extends JpaRepository<Pelicula, Long> {
    List<Pelicula> findAllByTitulo(String titulo);

    @Query("SELECT p from Pelicula p LEFT JOIN p.genero g where g.id=?1")
    List<Pelicula> findAllByGeneroId(Long id);

    List<Pelicula> findAllByOrderByCreateAtAsc();

    List<Pelicula> findAllByOrderByCreateAtDesc();
}
