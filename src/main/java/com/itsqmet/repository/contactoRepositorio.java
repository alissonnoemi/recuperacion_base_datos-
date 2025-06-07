package com.itsqmet.repository;

import com.itsqmet.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface contactoRepositorio extends JpaRepository <Contacto, Long> {
    List <Contacto> findByEmailContainingIgnoreCase(String email);

}
