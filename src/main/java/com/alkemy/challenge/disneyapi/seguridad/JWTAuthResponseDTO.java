package com.alkemy.challenge.disneyapi.seguridad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class JWTAuthResponseDTO {
    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

    public JWTAuthResponseDTO(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }
}
