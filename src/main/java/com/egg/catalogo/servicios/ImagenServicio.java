package com.egg.catalogo.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.egg.catalogo.entidades.Imagen;
import com.egg.catalogo.excepciones.MiExcepcion;
import com.egg.catalogo.repositorios.ImagenRepositorio;

@Service
public class ImagenServicio {
    
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public Imagen guardar(MultipartFile archivo) throws MiExcepcion {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiExcepcion {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public void eliminar(String id) {
        imagenRepositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Imagen> listarTodos() {
        return imagenRepositorio.findAll();
    }
}
