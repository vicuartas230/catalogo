package com.egg.catalogo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.egg.catalogo.servicios.ArticuloServicio;
import com.egg.catalogo.servicios.ImagenServicio;
import com.egg.catalogo.servicios.UsuarioServicio;
import com.egg.catalogo.entidades.Articulo;
import com.egg.catalogo.entidades.Usuario;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private ArticuloServicio articuloServicio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {
        Usuario usuario = usuarioServicio.getOne(id);
        byte[] imagen = usuario.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @PostMapping("/perfil/{id}")
    public ResponseEntity<String> actualizarImagenPerfil(@PathVariable String id, @RequestParam("archivo") MultipartFile archivo) {
        try {
            imagenServicio.actualizar(archivo, id);
            return new ResponseEntity<>("Imagen actualizada exitosamente!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la imagen", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/articulo/{id}")
    public ResponseEntity<byte[]> imagenArticulo(@PathVariable String id) {
        Articulo articulo = articuloServicio.getOne(id);
        byte[] imagen = articulo.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @PostMapping("/articulo/{id}")
    public ResponseEntity<String> actualizarImagenArticulo(@PathVariable String id, @RequestParam("archivo") MultipartFile archivo) {
        try {
            imagenServicio.actualizar(archivo, id);
            return new ResponseEntity<>("Imagen actualizada exitosamente!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la imagen", HttpStatus.BAD_REQUEST);
        }
    }
}
