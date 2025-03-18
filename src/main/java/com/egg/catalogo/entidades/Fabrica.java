package com.egg.catalogo.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fabrica {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idFabrica;

    @Column(nullable = false)
    private String nombreFabrica;

    public Fabrica() {
    }

    public String getId() {
        return idFabrica;
    }

    public void setId(String id) {
        this.idFabrica = id;
    }

    public String getNombre() {
        return nombreFabrica;
    }

    public void setNombre(String nombre) {
        this.nombreFabrica = nombre;
    }
}
