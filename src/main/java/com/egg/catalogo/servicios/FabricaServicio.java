package com.egg.catalogo.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.catalogo.entidades.Fabrica;
import com.egg.catalogo.excepciones.MiExcepcion;
import com.egg.catalogo.repositorios.FabricaRepositorio;

@Service
public class FabricaServicio {
    
    @Autowired
    private FabricaRepositorio fabricaRepositorio;

    @Transactional
    public void crearFabrica(String nombre) throws MiExcepcion {
        validar(nombre);
        Fabrica fabrica = new Fabrica();
        fabrica.setNombre(nombre);
        fabricaRepositorio.save(fabrica);
    }

    @Transactional(readOnly = true)
    public List<Fabrica> listarFabricas() {
        return fabricaRepositorio.findAll();
    }

    @Transactional
    public void modificarFabrica(String id, String nombre) throws MiExcepcion {
        validar(nombre);
        Optional<Fabrica> respuesta = fabricaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Fabrica fabrica = respuesta.get();
            fabrica.setNombre(nombre);
            fabricaRepositorio.save(fabrica);
        }
    }

    @Transactional
    public Fabrica getOne(String id) {
        return fabricaRepositorio.getReferenceById(id);
    }

    @Transactional
    public void eliminarFabrica(String id) {
        fabricaRepositorio.deleteById(id);
    }

    private void validar(String nombre) throws MiExcepcion {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre de la fábrica no puede ser nulo o vacío");
        }
    }
}
