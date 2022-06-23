package com.alkemy.challenge.disneyapi.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Personaje implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer edad;
    private Double peso;
    private String historia;
    private String imagen;
    @ManyToMany(mappedBy = "personajes")
    //JsonIgnoreProperties("personajes")
    @JsonIgnore
    private Set<Pelicula> peliculas = new HashSet<>();

    private static final long serialVersionUID = 1L;
}
