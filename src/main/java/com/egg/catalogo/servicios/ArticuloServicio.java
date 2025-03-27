package com.egg.catalogo.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.egg.catalogo.entidades.Articulo;
import com.egg.catalogo.entidades.Fabrica;
import com.egg.catalogo.entidades.Imagen;
import com.egg.catalogo.excepciones.MiExcepcion;
import com.egg.catalogo.repositorios.ArticuloRepositorio;
import com.egg.catalogo.repositorios.FabricaRepositorio;

@Service
public class ArticuloServicio {
    
    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    @Autowired
    private FabricaRepositorio fabricaRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearArticulo(String nombreArticulo, String descripcionArticulo, String idFabrica, MultipartFile archivo) throws MiExcepcion {
        validar(nombreArticulo, descripcionArticulo);
        Articulo articulo = new Articulo();
        articulo.setNombreArticulo(nombreArticulo);
        articulo.setDescripcionArticulo(descripcionArticulo);
        Fabrica fabrica = fabricaRepositorio.findById(idFabrica).get();
        articulo.setFabrica(fabrica);
        if (!archivo.isEmpty()) {
            Imagen imagen = imagenServicio.guardar(archivo);
            articulo.setImagen(imagen);
        }
        articuloRepositorio.save(articulo);
    }

    @Transactional(readOnly = true)
    public List<Articulo> listarArticulos() {
        return articuloRepositorio.findAll();
    }

    @Transactional
    public void modificarArticulo(String id, String nombreArticulo, String descripcionArticulo, String idFabrica, MultipartFile archivo) throws MiExcepcion {
        validar(nombreArticulo, descripcionArticulo);
        Optional<Articulo> respuesta = articuloRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Articulo articulo = respuesta.get();
            articulo.setNombreArticulo(nombreArticulo);
            articulo.setDescripcionArticulo(descripcionArticulo);
            Fabrica fabrica = fabricaRepositorio.findById(idFabrica).get();
            articulo.setFabrica(fabrica);
            if (!archivo.isEmpty()) {
                Imagen imagen = imagenServicio.guardar(archivo);
                articulo.setImagen(imagen);
            }
            articuloRepositorio.save(articulo);
        }
    }

    @Transactional(readOnly = true)
    public Articulo getOne(String id) {
        return articuloRepositorio.getReferenceById(id);
    }

    @Transactional
    public void eliminarArticulo(String id) throws MiExcepcion {
        articuloRepositorio.deleteById(id);
    }

    private void validar(String nombreArticulo, String descripcionArticulo) {
        if (nombreArticulo == null || nombreArticulo.isEmpty()) {
            throw new RuntimeException("El nombre del artículo no puede ser nulo o vacío");
        }
        if (descripcionArticulo == null || descripcionArticulo.isEmpty()) {
            throw new RuntimeException("La descripción del artículo no puede ser nula o vacía");
        }
    }       
}
