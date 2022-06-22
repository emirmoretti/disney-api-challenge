package com.alkemy.challenge.disneyapi.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonajeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String imagen;

}
