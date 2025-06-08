package com.itsqmet.repository;

import com.itsqmet.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clienteRepositorio extends JpaRepository<Cliente, Long> {
    Cliente findByEmail(String email);
}