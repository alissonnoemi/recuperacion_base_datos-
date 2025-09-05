package com.itsqmet.repository;

import com.itsqmet.entity.Servicio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface servicioRepositorio extends MongoRepository<Servicio, Long> {
    List<Servicio> findByNegocio_IdNegocio(Long idNegocio);

}
