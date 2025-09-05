package com.itsqmet.repository;

import com.itsqmet.entity.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface clienteRepositorio extends MongoRepository<Cliente, Long> {
   List <Cliente> findByEmailContainingIgnoreCase(String email);
   List<Cliente> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
}