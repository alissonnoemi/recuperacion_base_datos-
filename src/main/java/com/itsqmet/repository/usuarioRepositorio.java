package com.itsqmet.repository;

import com.itsqmet.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface usuarioRepositorio extends JpaRepository <Usuario, Long> {
    List<Usuario> findByEmailContainingIgnoreCase(String email);
}
