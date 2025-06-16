package com.itsqmet.service;

import com.itsqmet.entity.*;
import com.itsqmet.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CitasServicio {
    @Autowired
    private citasRepositorio citasRepositorio;
    @Autowired
    private clienteRepositorio pacienteRepositorio;
    @Autowired
    private servicioRepositorio servicioRepositorio;
    @Autowired
    private profesionalRepositorio profesionalRepositorio;
    @Autowired
    private negocioRepositorio negocioRepositorio;

    @Transactional
    public Citas agendarNuevaCita(Citas nuevaCita) throws Exception {
        // Validación de IDs y recuperación de entidades
        Cliente cliente = pacienteRepositorio.findById(nuevaCita.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con ID: " + nuevaCita.getCliente().getId()));
        Servicio servicio = servicioRepositorio.findById(nuevaCita.getServicio().getIdServicio())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + nuevaCita.getServicio().getIdServicio()));
        Profesional profesional = profesionalRepositorio.findById(nuevaCita.getProfesional().getIdProfesional())
                .orElseThrow(() -> new IllegalArgumentException("Profesional no encontrado con ID: " + nuevaCita.getProfesional().getIdProfesional()));

        nuevaCita.setCliente(cliente);
        nuevaCita.setServicio(servicio);
        nuevaCita.setProfesional(profesional);

        if (nuevaCita.getFechaHoraInicio() == null) {
            throw new IllegalArgumentException("La fecha y hora de inicio de la cita es obligatoria.");
        }

        if (nuevaCita.getDuracionServicioMinutos() == null || nuevaCita.getDuracionServicioMinutos() <= 0) {
            nuevaCita.setDuracionServicioMinutos(servicio.getDuracionMinutos());
        }
        nuevaCita.setFechaHoraFin(nuevaCita.getFechaHoraInicio().plusMinutes(nuevaCita.getDuracionServicioMinutos()));
        List<Citas> citasConflictivas = citasRepositorio.findConflictingAppointments(
                profesional, nuevaCita.getFechaHoraInicio(), nuevaCita.getFechaHoraFin());

        if (!citasConflictivas.isEmpty()) {
            throw new IllegalArgumentException("Ya existe una cita agendada para el profesional '" +
                    profesional.getNombreCompleto() + "' en el horario seleccionado (" +
                    nuevaCita.getFechaHoraInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " - " +
                    nuevaCita.getFechaHoraFin().format(DateTimeFormatter.ofPattern("HH:mm")) + ").");
        }
        if (nuevaCita.getEstadoCita() == null) { // Si el estado no se envía, se establece por defecto
            nuevaCita.setEstadoCita(Citas.EstadoCita.PENDIENTE);
        }

        return citasRepositorio.save(nuevaCita);
    }

    public List<Citas> obtenerTodosLosCitas() {
        return citasRepositorio.findAll();
    }

    public Optional<Citas> buscarCitaPorId(Long id) {
        return citasRepositorio.findById(id);
    }

    @Transactional
    public Citas actualizarCita(Long id, Citas citaActualizada) throws Exception {
        Citas citaExistente = citasRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + id));

        // Actualizar campos directos
        if (citaActualizada.getFechaHoraInicio() != null) {
            citaExistente.setFechaHoraInicio(citaActualizada.getFechaHoraInicio());
        }
        if (citaActualizada.getDuracionServicioMinutos() != null && citaActualizada.getDuracionServicioMinutos() > 0) {
            citaExistente.setDuracionServicioMinutos(citaActualizada.getDuracionServicioMinutos());
        } else if (citaExistente.getServicio() != null && citaExistente.getServicio().getDuracionMinutos() != null) {
            // Recalcular duración si se cambia el servicio y no se especifica duración
            citaExistente.setDuracionServicioMinutos(citaExistente.getServicio().getDuracionMinutos());
        }
        citaExistente.setFechaHoraFin(citaExistente.getFechaHoraInicio().plusMinutes(citaExistente.getDuracionServicioMinutos()));

        if (citaActualizada.getEstadoCita() != null) {
            citaExistente.setEstadoCita(citaActualizada.getEstadoCita());
        }

        // Actualizar relaciones si los IDs han cambiado
        if (citaActualizada.getCliente() != null && citaActualizada.getCliente().getId() != null &&
                !citaExistente.getCliente().getId().equals(citaActualizada.getCliente().getId())) {
            Cliente nuevoPaciente = pacienteRepositorio.findById(citaActualizada.getCliente().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Nuevo Paciente no encontrado con ID: " + citaActualizada.getCliente().getId()));
            citaExistente.setCliente(nuevoPaciente);
        }

        if (citaActualizada.getServicio() != null && citaActualizada.getServicio().getIdServicio() != null &&
                !citaExistente.getServicio().getIdServicio().equals(citaActualizada.getServicio().getIdServicio())) {
            Servicio nuevoServicio = servicioRepositorio.findById(citaActualizada.getServicio().getIdServicio())
                    .orElseThrow(() -> new IllegalArgumentException("Nuevo Servicio no encontrado con ID: " + citaActualizada.getServicio().getIdServicio()));
            citaExistente.setServicio(nuevoServicio);
            // Si el servicio cambia, recalcula la duración si no se especificó una nueva
            if (citaActualizada.getDuracionServicioMinutos() == null || citaActualizada.getDuracionServicioMinutos() <= 0) {
                citaExistente.setDuracionServicioMinutos(nuevoServicio.getDuracionMinutos());
                citaExistente.setFechaHoraFin(citaExistente.getFechaHoraInicio().plusMinutes(citaExistente.getDuracionServicioMinutos()));
            }
        }

        if (citaActualizada.getProfesional() != null && citaActualizada.getProfesional().getIdProfesional() != null &&
                !citaExistente.getProfesional().getIdProfesional().equals(citaActualizada.getProfesional().getIdProfesional())) {
            Profesional nuevoProfesional = profesionalRepositorio.findById(citaActualizada.getProfesional().getIdProfesional())
                    .orElseThrow(() -> new IllegalArgumentException("Nuevo Profesional no encontrado con ID: " + citaActualizada.getProfesional().getIdProfesional()));
            citaExistente.setProfesional(nuevoProfesional);
        }

        // *** Lógica de validación de superposición para actualización ***
        List<Citas> citasConflictivas = citasRepositorio.findConflictingAppointmentsExcludingSelf(
                citaExistente.getProfesional(), citaExistente.getFechaHoraInicio(), citaExistente.getFechaHoraFin(), citaExistente.getIdCita());

        if (!citasConflictivas.isEmpty()) {
            throw new IllegalArgumentException("Ya existe una cita agendada para el profesional '" +
                    citaExistente.getProfesional().getNombreCompleto() + "' en el horario seleccionado (" +
                    citaExistente.getFechaHoraInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " - " +
                    citaExistente.getFechaHoraFin().format(DateTimeFormatter.ofPattern("HH:mm")) + ").");
        }
        // *** Fin de la lógica de validación de superposición para actualización ***

        return citasRepositorio.save(citaExistente);
    }

    public void eliminarCita(Long id) {
        if (!citasRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Cita no encontrada con ID: " + id);
        }
        citasRepositorio.deleteById(id);
    }

    @Transactional
    public List<Citas> obtenerCitasPorNegocio(Long negocioId) {
        Negocio negocio = negocioRepositorio.findById(negocioId)
                .orElseThrow(() -> new IllegalArgumentException("Negocio no encontrado con ID: " + negocioId));
        return citasRepositorio.findByProfesional_NegocioOrderByFechaHoraInicioAsc(negocio);
    }

    @Transactional
    public List<Citas> obtenerCitasPorProfesional(Long profesionalId) {
        Profesional profesional = profesionalRepositorio.findById(profesionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profesional no encontrado con ID: " + profesionalId));
        return citasRepositorio.findByProfesionalOrderByFechaHoraInicioAsc(profesional);
    }
}
