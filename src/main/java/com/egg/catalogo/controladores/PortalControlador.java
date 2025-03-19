package com.egg.catalogo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.catalogo.excepciones.MiExcepcion;
import com.egg.catalogo.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrousuario.html";
    }

    @PostMapping("/registro")
    public String registro(
        @RequestParam String nombre,
        @RequestParam String apellido,
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam String password2,
        ModelMap modelo
    ) {
        try {
            usuarioServicio.registrarUsuario(nombre, apellido, email, password, password2);
            modelo.put("exito", "Usuario registrado con éxito");
            return "redirect:/";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            return "registrousuario.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña incorrectos");
        }
        return "login.html";
    }

    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }
}
