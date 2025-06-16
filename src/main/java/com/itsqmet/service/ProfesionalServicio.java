package com.itsqmet.service;

import com.itsqmet.entity.Negocio;
import com.itsqmet.entity.Profesional;
import com.itsqmet.repository.negocioRepositorio;
import com.itsqmet.repository.profesionalRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesionalServicio {

    @Autowired
    private profesionalRepositorio profesionalRepositorio;
    @Autowired
    private negocioRepositorio negocioRepositorio; // Para validar la existencia del negocio

    public List<Profesional> obtenerTodosLosProfesionales() {
        return profesionalRepositorio.findAll();
    }

    public Optional<Profesional> obtenerProfesionalPorId(Long id) {
        return profesionalRepositorio.findById(id);
    }

    @Transactional
    public Profesional guardarProfesional(Profesional profesional) {
        // Validar que el negocio asociado exista
        if (profesional.getNegocio() == null || profesional.getNegocio().getIdNegocio() == null) {
            throw new IllegalArgumentException("El negocio asociado es obligatorio.");
        }
        Negocio negocio = negocioRepositorio.findById(profesional.getNegocio().getIdNegocio())
                .orElseThrow(() -> new IllegalArgumentException("Negocio no encontrado con ID: " + profesional.getNegocio().getIdNegocio()));

        profesional.setNegocio(negocio); // Asegura que el objeto negocio esté gestionado por JPA
        return profesionalRepositorio.save(profesional);
    }

    @Transactional
    public Profesional editarProfesional(Long id, Profesional profesionalActualizado) {
        Profesional profesionalExistente = profesionalRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profesional no encontrado con ID: " + id));
        profesionalExistente.setNombreCompleto(profesionalActualizado.getNombreCompleto());
        profesionalExistente.setEspecialidad(profesionalActualizado.getEspecialidad());
        profesionalExistente.setTelefono(profesionalActualizado.getTelefono());
        profesionalExistente.setEmail(profesionalActualizado.getEmail());
        return profesionalRepositorio.save(profesionalExistente);
    }

    public void eliminarProfesional(Long id) {
        if (!profesionalRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Profesional no encontrado con ID: " + id);
        }
        profesionalRepositorio.deleteById(id);
    }

    public List<Profesional> obtenerProfesionalesPorNegocio(Long idNegocio) {
        return profesionalRepositorio.findByNegocio_IdNegocio(idNegocio);
    }
}
