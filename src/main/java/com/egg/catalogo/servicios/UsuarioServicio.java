package com.egg.catalogo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.egg.catalogo.entidades.Imagen;
import com.egg.catalogo.entidades.Usuario;
import com.egg.catalogo.enumeraciones.Rol;
import com.egg.catalogo.excepciones.MiExcepcion;
import com.egg.catalogo.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

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
    public void registrarUsuario(MultipartFile archivo, String nombre, String apellido, String email, String password, String password2) throws MiExcepcion {
        validar(nombre, apellido, email, password, password2);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setRol(Rol.USER);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        if (!archivo.isEmpty()) {
            Imagen imagen = imagenServicio.guardar(archivo);
            usuario.setImagen(imagen);
        }
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

    @Transactional
    public void modificarUsuario(MultipartFile archivo, String id, String nombre, String apellido, String email, String rol, String password, String password2) throws MiExcepcion {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            validar(nombre, apellido, email, password, password2);
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setRol(rol.equals("ADMIN") ? Rol.ADMIN : Rol.USER);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            Imagen imagen = usuario.getImagen();
            if(!archivo.isEmpty()) {
                imagenServicio.eliminar(imagen.getId());
                Imagen nuevaImagen = imagenServicio.guardar(archivo);
                usuario.setImagen(nuevaImagen);
            }
            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void eliminarUsuario(String id) throws MiExcepcion {
        usuarioRepositorio.deleteById(id);
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
