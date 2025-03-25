package com.egg.catalogo.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.catalogo.entidades.Fabrica;
import com.egg.catalogo.excepciones.MiExcepcion;
import com.egg.catalogo.servicios.FabricaServicio;

@Controller
@RequestMapping("/fabrica")
public class FabricaControlador {

    @Autowired
    private FabricaServicio fabricaServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "fabrica_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        try {
            fabricaServicio.crearFabrica(nombre);
            modelo.put("exito", "Fabrica registrada con exito");
            return "redirect:/fabrica/lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "fabrica_form.html";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Fabrica> fabricas = fabricaServicio.listarFabricas();
        modelo.addAttribute("fabricas", fabricas);
        return "fabrica_lista.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        Fabrica fabrica = fabricaServicio.getOne(id);
        modelo.put("fabrica", fabrica);
        return "fabrica_modificar.html";
    }

    @PostMapping("{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {
        try {
            fabricaServicio.modificarFabrica(id, nombre);
            modelo.put("exito", "Fabrica modificada con exito");
            return "redirect:/fabrica/lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "fabrica_modificar.html";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        fabricaServicio.eliminarFabrica(id);
        return "redirect:/fabrica/lista";
    }
}
