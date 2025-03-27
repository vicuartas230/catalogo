package com.egg.catalogo.controladores;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.egg.catalogo.entidades.Usuario;
import com.egg.catalogo.enumeraciones.Rol;
import com.egg.catalogo.excepciones.MiExcepcion;
import com.egg.catalogo.servicios.UsuarioServicio;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/lista")
    public String lista(ModelMap modelo) throws MiExcepcion {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_lista.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) throws MiExcepcion {
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.addAttribute("roles", Rol.getRoles());
        modelo.addAttribute("usuario", usuario);
        return "usuario_modificar.html";
    }

    @PostMapping("{id}")
    public String modificar(
        @PathVariable String id,
        String nombre,
        String apellido,
        String email,
        String rol,
        MultipartFile archivo,
        String password,
        String password2,
        ModelMap modelo
    ) throws MiExcepcion {
        try {
            usuarioServicio.modificarUsuario(archivo, id, nombre, apellido, email, rol, password, password2);
            return "redirect:/admin/lista";
        } catch (MiExcepcion ex) {
            modelo.addAttribute("nombre", nombre);
            modelo.addAttribute("apellido", apellido);
            modelo.addAttribute("email", email);
            return "usuario_modificar.html";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        try {
            usuarioServicio.eliminarUsuario(id);
            return "redirect:/admin/lista";
        } catch (MiExcepcion ex) {
            System.out.println(ex.getMessage());
            return "redirect:/admin/lista";
        }
    }
}
