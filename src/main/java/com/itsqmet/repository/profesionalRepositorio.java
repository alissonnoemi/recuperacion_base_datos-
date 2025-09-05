package com.itsqmet.repository;

import com.itsqmet.entity.Profesional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface profesionalRepositorio extends MongoRepository<Profesional, Long> {
    List<Profesional> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
    List<Profesional> findByNegocio_IdNegocio(Long idNegocio);
}
