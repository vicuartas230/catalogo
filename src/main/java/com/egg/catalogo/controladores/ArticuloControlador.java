package com.egg.catalogo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.egg.catalogo.excepciones.MiExcepcion;
import com.egg.catalogo.servicios.ArticuloServicio;
import com.egg.catalogo.servicios.FabricaServicio;

@Controller
@RequestMapping("/articulo")
public class ArticuloControlador {
    
    @Autowired
    private ArticuloServicio articuloServicio;

    @Autowired
    private FabricaServicio fabricaServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        modelo.addAttribute("fabricas", fabricaServicio.listarFabricas());
        return "articulo_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String descripcion, @RequestParam String idFabrica, @RequestParam MultipartFile archivo, ModelMap modelo) {
        try {
            articuloServicio.crearArticulo(nombre, descripcion, idFabrica, archivo);
            modelo.put("exito", "Articulo registrado con exito");
            return "redirect:/articulo/lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("descripcion", descripcion);
            return "articulo_form.html";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        modelo.addAttribute("articulos", articuloServicio.listarArticulos());
        return "articulo_lista.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.addAttribute("articulo", articuloServicio.getOne(id));
        modelo.addAttribute("fabricas", fabricaServicio.listarFabricas());
        return "articulo_modificar.html";
    }

    @PostMapping("{id}")
    public String modificar(@PathVariable String id, String nombre, String descripcion, String idFabrica, ModelMap modelo, MultipartFile archivo) {
        try {
            articuloServicio.modificarArticulo(id, nombre, descripcion, idFabrica, archivo);
            modelo.put("exito", "Artículo modificado con éxito!");
            return "redirect:/articulo/lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("descripcion", descripcion);
            return "articulo_modificar.html";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        try {
            articuloServicio.eliminarArticulo(id);
            modelo.put("exito", "Artículo eliminado con éxito!");
            return "redirect:/articulo/lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/articulo/lista";
        }
    }
}
