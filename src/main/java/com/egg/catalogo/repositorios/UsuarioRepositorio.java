package com.egg.catalogo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.egg.catalogo.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarPorEmail(String email);
}
