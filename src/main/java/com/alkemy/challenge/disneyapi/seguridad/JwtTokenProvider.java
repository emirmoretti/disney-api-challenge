package com.alkemy.challenge.disneyapi.seguridad;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generarToken(Authentication authentication){
        String username = authentication.getName();
        Date fechaAct = new Date();
        Date fechaExp = new Date(fechaAct.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date()).setExpiration(fechaExp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return token;
    }
    public String obtenerUsernameDelJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public boolean validarToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new RuntimeException("firma no valida");
        } catch (MalformedJwtException ex){
            throw new RuntimeException("token jwt no valida");
        }   catch (ExpiredJwtException ex){
            throw new RuntimeException("token jwt expiro");
        }   catch (UnsupportedJwtException ex){
            throw new RuntimeException("token jwt no compatible");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Jwt esta vacio");
        }
    }
}
