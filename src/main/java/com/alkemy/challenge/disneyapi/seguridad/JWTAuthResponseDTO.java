package com.alkemy.challenge.disneyapi.seguridad;

import lombok.Data;

@Data
public class JWTAuthResponseDTO {
    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";
}
