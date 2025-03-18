package com.egg.catalogo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.catalogo.entidades.Articulo;

@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo, String> {
}
