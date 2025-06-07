package com.itsqmet.service;

import com.itsqmet.entity.Plan;
import com.itsqmet.repository.planRepositorio;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlanServicio {
    private planRepositorio planRepositorio;
    public List<Plan> mostrarPlanes() {
        return planRepositorio.findAll();
    }
    public List<Plan> buscarPlanPorNombre(String buscarNombre) {
        if (buscarNombre == null || buscarNombre.isEmpty()) {
            return planRepositorio.findAll();
        } else {
            return planRepositorio.findByNombrePlanContainingIgnoreCase(buscarNombre);
        }
    }
    public Plan guardarPlan(Plan plan) {
        return planRepositorio.save(plan);
    }
    public void eliminarPlan(Long id) {
        planRepositorio.deleteById(id);
    }
}
