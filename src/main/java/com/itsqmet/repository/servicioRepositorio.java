package com.itsqmet.repository;

import com.itsqmet.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface servicioRepositorio extends JpaRepository<Servicio, Long> {
    List<Servicio> findByNegocio_IdNegocio(Long idNegocio);

}
