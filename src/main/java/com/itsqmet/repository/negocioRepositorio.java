package com.itsqmet.repository;

import com.itsqmet.entity.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface negocioRepositorio extends JpaRepository <Negocio, Long> {
    List <Negocio> findByNombreNegocioContainingIgnoreCase(String nombreNegocio);

}
