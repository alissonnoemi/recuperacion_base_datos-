package com.itsqmet.repository;

import com.itsqmet.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface planRepositorio extends JpaRepository <Plan, Long> {
    List <Plan> findByNombrePlanContainingIgnoreCase(String nombre);
}
