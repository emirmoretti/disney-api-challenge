package com.alkemy.challenge.disneyapi.controllers;

import com.alkemy.challenge.disneyapi.dto.LoginDTO;
import com.alkemy.challenge.disneyapi.dto.RegistroDTO;
import com.alkemy.challenge.disneyapi.entity.Role;
import com.alkemy.challenge.disneyapi.entity.Usuario;
import com.alkemy.challenge.disneyapi.repo.RoleRepo;
import com.alkemy.challenge.disneyapi.repo.UsuarioRepo;
import com.alkemy.challenge.disneyapi.seguridad.JWTAuthResponseDTO;
import com.alkemy.challenge.disneyapi.seguridad.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //obtenemos el token de jwttokenprovider
        String token = jwtTokenProvider.generarToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistroDTO registroDTO){
        if(usuarioRepo.existsByUsername(registroDTO.getUsername())){
            return new ResponseEntity<>("Usuario ya existente", HttpStatus.BAD_REQUEST);
        }
        if(usuarioRepo.existsByEmail(registroDTO.getEmail())){
            return new ResponseEntity<>("Usuario ya existente", HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Role roles = roleRepo.findByNombre("ROLE_ADMIN").get();
        usuario.setRoles(Collections.singleton(roles));

        usuarioRepo.save(usuario);
        return new ResponseEntity<>("usuario creado", HttpStatus.OK);
    }

}
