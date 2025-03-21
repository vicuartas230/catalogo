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
    private static final AtomicInteger atomicInteger = new AtomicInteger(1);
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

    public Articulo() {
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Integer getNroArticulo() {
        return nroArticulo;
    }

    public void setNroArticulo() {
        this.nroArticulo = atomicInteger.incrementAndGet();
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public Fabrica getFabrica() {
        return fabrica;
    }

    public void setFabrica(Fabrica fabrica) {
        this.fabrica = fabrica;
    }
}
