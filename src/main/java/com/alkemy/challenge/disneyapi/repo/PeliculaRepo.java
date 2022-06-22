package com.alkemy.challenge.disneyapi.repo;

import com.alkemy.challenge.disneyapi.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepo extends JpaRepository<Pelicula, Long> {

}
