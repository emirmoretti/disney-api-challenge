package com.alkemy.challenge.disneyapi.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PeliculaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titulo;
    private Date fecha;
    private String image;

}
