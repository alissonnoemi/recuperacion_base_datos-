package com.itsqmet.service;

import com.itsqmet.entity.Negocio;
import com.itsqmet.entity.Profesional;
import com.itsqmet.repository.negocioRepositorio;
import com.itsqmet.repository.profesionalRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // ¡Importante añadir esta importación!
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NegocioServicio {

    @Autowired
    private negocioRepositorio negocioRepositorio;
    @Autowired
    private profesionalRepositorio profesionalRepositorio;

    // --- Métodos existentes (sin cambios mayores) ---

    public List<Negocio> obtenerTodosLosNegocios() {
        return negocioRepositorio.findAll();
    }

    public Page<Negocio> obtenerTodosLosNegocios(Pageable pageable) {
        return negocioRepositorio.findAll(pageable);
    }

    public Page<Negocio> buscarNegocios(String search, Pageable pageable) {
        return negocioRepositorio.findByNombreNegocioContainingIgnoreCaseOrEmailProfesionalContainingIgnoreCase(search, search, pageable);
    }

    public Optional<Negocio> obtenerNegocioPorId(Long id) {
        return negocioRepositorio.findById(id);
    }

    @Transactional
    public Negocio guardarNegocio(Negocio negocio) {
        if (negocio.getNombreNegocio() == null || negocio.getNombreNegocio().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del negocio no puede estar vacío.");
        }
        // Este método ya está recibiendo un objeto Negocio que, a su vez, ya tiene el 'plan' seteado
        // desde el DTO en el controlador. Así que no necesita cambios aquí.
        return negocioRepositorio.save(negocio);
    }

    @Transactional
    public Negocio editarNegocio(Long id, Negocio negocioActualizado) {
        Negocio negocioExistente = negocioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Negocio no encontrado con ID: " + id));

        negocioExistente.setNombreCompleto(negocioActualizado.getNombreCompleto());
        negocioExistente.setDireccion(negocioActualizado.getDireccion());
        negocioExistente.setTelefono(negocioActualizado.getTelefono());
        negocioExistente.setEmailProfesional(negocioActualizado.getEmailProfesional());
        negocioExistente.setTipoNegocio(negocioActualizado.getTipoNegocio()); // Asegúrate de actualizar también el tipo de negocio

        // --- ¡CAMBIO CRÍTICO AQUÍ! ---
        // Debes actualizar el plan del negocio existente con el plan del negocioActualizado
        // si se permite la edición del plan desde el formulario de edición.
        // Si el formulario de edición no permite cambiar el plan, esta línea podría omitirse
        // o requerir una lógica más compleja para "actualizar plan".
        negocioExistente.setPlan(negocioActualizado.getPlan());


        return negocioRepositorio.save(negocioExistente);
    }

    // --- Métodos adicionales (sin cambios mayores) ---

    public List<Negocio> mostrarNegocios() {
        return negocioRepositorio.findAll();
    }

    public List<Negocio> buscarNegocioPorNombre(String buscarNegocio) {
        if (buscarNegocio == null || buscarNegocio.isEmpty()) {
            return negocioRepositorio.findAll();
        } else {
            return negocioRepositorio.findByTipoNegocioContainingIgnoreCase(buscarNegocio);
        }
    }

    public Optional<Negocio> buscarNegocioPorId(Long id) {
        return negocioRepositorio.findById(id);
    }

    @Transactional
    public Profesional guardarProfesional(Profesional profesional) {
        if (profesional.getNegocio() == null || profesional.getNegocio().getIdNegocio() == null) {
            throw new IllegalArgumentException("El negocio asociado es obligatorio.");
        }
        Negocio negocio = negocioRepositorio.findById(profesional.getNegocio().getIdNegocio())
                .orElseThrow(() -> new IllegalArgumentException("Negocio no encontrado con ID: " + profesional.getNegocio().getIdNegocio()));

        profesional.setNegocio(negocio);
        return profesionalRepositorio.save(profesional);
    }

    @Transactional
    public void eliminarNegocio(Long id) {
        negocioRepositorio.deleteById(id);
    }
}