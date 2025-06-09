package com.itsqmet.repository;

import com.itsqmet.entity.LogNegocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface loginNegocioRepositorio extends JpaRepository <LogNegocio, Long> {
List <LogNegocio> findByEmailContainingIgnoreCase(String email);
}
