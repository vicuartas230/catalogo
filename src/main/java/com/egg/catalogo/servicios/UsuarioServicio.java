package com.egg.catalogo.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.egg.catalogo.entidades.Usuario;
import com.egg.catalogo.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

    @Transactional
    public void registrarUsuario(String nombre, String apellido, String email, String password, String password2) {
        validar(nombre, apellido, email, password, password2);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public Usuario getOne(String id) {
        return usuarioRepositorio.getReferenceById(id);
    }

    @Transactional
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    private void validar(String nombre, String apellido, String email, String password, String password2) {
        if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre no puede ser nulo o vacío");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new RuntimeException("El apellido no puede ser nulo o vacío");
        }
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("El email no puede ser nulo o vacío");
        }
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("La contraseña no puede ser nula o vacía");
        }
        if (!password.equals(password2)) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }
    }
}
