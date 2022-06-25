package com.alkemy.challenge.disneyapi.repo;

import com.alkemy.challenge.disneyapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

        Optional<Usuario> findByEmail(String email);

        Optional<Usuario> findByUsernameOrEmail(String username, String email);

        Optional<Usuario> findByUsername(String username);

        Boolean existsByUsername(String username);

        Boolean existsByEmail(String email);

}
