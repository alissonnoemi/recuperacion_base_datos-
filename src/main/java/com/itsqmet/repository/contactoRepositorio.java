package com.itsqmet.repository;

import com.itsqmet.entity.Contacto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface contactoRepositorio extends MongoRepository<Contacto, Long> {
}