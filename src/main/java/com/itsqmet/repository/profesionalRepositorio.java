package com.itsqmet.repository;

import com.itsqmet.entity.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface profesionalRepositorio extends JpaRepository<Profesional, Long> {
    List<Profesional> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
    List<Profesional> findByNegocio_IdNegocio(Long idNegocio);
}
