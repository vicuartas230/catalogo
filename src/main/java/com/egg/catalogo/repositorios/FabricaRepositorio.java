package com.egg.catalogo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.catalogo.entidades.Fabrica;

@Repository
public interface FabricaRepositorio extends JpaRepository<Fabrica, String> {
}
