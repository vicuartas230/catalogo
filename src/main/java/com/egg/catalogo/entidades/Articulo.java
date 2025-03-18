package com.egg.catalogo.entidades;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idArticulo;

    @Column(unique = true)
    private Integer nroArticulo;

    @Column(nullable = false)
    private String nombreArticulo;

    @Column(nullable = false)
    private String descripcionArticulo;

    @ManyToOne
    @JoinColumn(name = "id_fabrica")
    private Fabrica fabrica;
}
