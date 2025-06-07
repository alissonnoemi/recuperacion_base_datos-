package com.itsqmet.service;
import com.itsqmet.entity.Usuario;
import com.itsqmet.repository.usuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {
@Autowired
    private usuarioRepositorio usuarioRepositorio;
    public List<Usuario> mostrarUsuarios() {
        return usuarioRepositorio.findAll();
    }
    public List<Usuario> buscarUsuarioPorEmail(String buscarEmail) {
        if (buscarEmail == null || buscarEmail.isEmpty()) {
            return usuarioRepositorio.findAll();
        } else {
            return usuarioRepositorio.findByEmailContainingIgnoreCase(buscarEmail);
        }
    }
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id);
    }
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }
    public void eliminarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }

}
