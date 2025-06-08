package com.itsqmet.repository;

import com.itsqmet.entity.Citas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface citasRepositorio extends JpaRepository <Citas, Long> {
    List <Citas> findByEspecialistaContainingIgnoreCase(String especialista);

}
