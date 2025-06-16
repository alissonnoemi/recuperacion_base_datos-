package com.itsqmet.repository;

import com.itsqmet.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface clienteRepositorio extends JpaRepository<Cliente, Long> {
   List <Cliente> findByEmailContainingIgnoreCase(String email);
   List<Cliente> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
}