package com.itsqmet.service;

import com.itsqmet.entity.*;
import com.itsqmet.repository.*;
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
    private clienteRepositorio clienteRepositorio;

    @Autowired
    private servicioRepositorio servicioRepositorio;

    @Autowired
    private profesionalRepositorio profesionalRepositorio;

    @Autowired
    private negocioRepositorio negocioRepositorio;

    public Citas agendarNuevaCita(Citas nuevaCita) {
        Cliente cliente = clienteRepositorio.findById(nuevaCita.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + nuevaCita.getClienteId()));

        Servicio servicio = servicioRepositorio.findById(nuevaCita.getServicioId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + nuevaCita.getServicioId()));

        Profesional profesional = profesionalRepositorio.findById(nuevaCita.getProfesionalId())
                .orElseThrow(() -> new IllegalArgumentException("Profesional no encontrado con ID: " + nuevaCita.getProfesionalId()));

        // Validar fecha
        if (nuevaCita.getFechaHoraInicio() == null) {
            throw new IllegalArgumentException("La fecha y hora de inicio de la cita es obligatoria.");
        }

        // Si la duración no se especifica, usar la del servicio
        if (nuevaCita.getDuracionServicioHoras() == null || nuevaCita.getDuracionServicioHoras() <= 0) {
            nuevaCita.setDuracionServicioHoras(servicio.getDuracionHoras());
        }

        // Calcular fecha de fin
        nuevaCita.setFechaHoraFin(
                nuevaCita.getFechaHoraInicio().plusMinutes(nuevaCita.getDuracionServicioHoras())
        );

        // Verificar conflictos de agenda
        List<Citas> citasConflictivas = citasRepositorio.findConflictingAppointments(
                profesional.getIdProfesional(),
                nuevaCita.getFechaHoraInicio(),
                nuevaCita.getFechaHoraFin()
        );

        if (!citasConflictivas.isEmpty()) {
            throw new IllegalArgumentException("Ya existe una cita agendada para el profesional '" +
                    profesional.getNombreCompleto() + "' en el horario seleccionado (" +
                    nuevaCita.getFechaHoraInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " - " +
                    nuevaCita.getFechaHoraFin().format(DateTimeFormatter.ofPattern("HH:mm")) + ").");
        }

        // Estado por defecto
        if (nuevaCita.getEstadoCita() == null) {
            nuevaCita.setEstadoCita(Citas.EstadoCita.PENDIENTE);
        }

        return citasRepositorio.save(nuevaCita);
    }

    public List<Citas> obtenerTodosLosCitas() {
        return citasRepositorio.findAll();
    }

    public Optional<Citas> buscarCitaPorId(String id) {
        return citasRepositorio.findById(id);
    }

    /**
     * Actualizar cita
     */
    public Citas actualizarCita(String id, Citas citaActualizada) {
        Citas citaExistente = citasRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + id));

        // Actualizar fecha inicio
        if (citaActualizada.getFechaHoraInicio() != null) {
            citaExistente.setFechaHoraInicio(citaActualizada.getFechaHoraInicio());
        }

        // Actualizar cliente
        if (citaActualizada.getClienteId() != null &&
                (citaExistente.getClienteId() == null || !citaExistente.getClienteId().equals(citaActualizada.getClienteId()))) {
            clienteRepositorio.findById(citaActualizada.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + citaActualizada.getClienteId()));
            citaExistente.setClienteId(citaActualizada.getClienteId());
        }

        // Actualizar servicio y recalcular duración
        String servicioIdActual = citaExistente.getServicioId();
        if (citaActualizada.getServicioId() != null &&
                (servicioIdActual == null || !servicioIdActual.equals(citaActualizada.getServicioId()))) {
            Servicio nuevoServicio = servicioRepositorio.findById(citaActualizada.getServicioId())
                    .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + citaActualizada.getServicioId()));
            citaExistente.setServicioId(nuevoServicio.getIdServicio());
            citaExistente.setDuracionServicioHoras(nuevoServicio.getDuracionHoras());
        }

        // Actualizar duración manual si se envía
        if (citaActualizada.getDuracionServicioHoras() != null && citaActualizada.getDuracionServicioHoras() > 0) {
            citaExistente.setDuracionServicioHoras(citaActualizada.getDuracionServicioHoras());
        }

        // Recalcular fecha de fin
        if (citaExistente.getFechaHoraInicio() != null && citaExistente.getDuracionServicioHoras() != null) {
            citaExistente.setFechaHoraFin(
                    citaExistente.getFechaHoraInicio().plusMinutes(citaExistente.getDuracionServicioHoras())
            );
        }

        // Actualizar profesional
        if (citaActualizada.getProfesionalId() != null &&
                (citaExistente.getProfesionalId() == null || !citaExistente.getProfesionalId().equals(citaActualizada.getProfesionalId()))) {
            profesionalRepositorio.findById(citaActualizada.getProfesionalId())
                    .orElseThrow(() -> new IllegalArgumentException("Profesional no encontrado con ID: " + citaActualizada.getProfesionalId()));
            citaExistente.setProfesionalId(citaActualizada.getProfesionalId());
        }

        // Actualizar estado
        if (citaActualizada.getEstadoCita() != null) {
            citaExistente.setEstadoCita(citaActualizada.getEstadoCita());
        }

        // Validar conflictos (excluyendo la propia cita)
        List<Citas> citasConflictivas = citasRepositorio.findConflictingAppointmentsExcludingSelf(
                citaExistente.getProfesionalId(),
                citaExistente.getFechaHoraInicio(),
                citaExistente.getFechaHoraFin(),
                citaExistente.getIdCita()
        );

        if (!citasConflictivas.isEmpty()) {
            throw new IllegalArgumentException("Ya existe una cita agendada para el profesional en ese horario.");
        }

        return citasRepositorio.save(citaExistente);
    }

    public void eliminarCita(String id) {
        if (!citasRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Cita no encontrada con ID: " + id);
        }
        citasRepositorio.deleteById(id);
    }

    public List<Citas> obtenerCitasPorNegocio(String negocioId) {
        negocioRepositorio.findById(negocioId)
                .orElseThrow(() -> new IllegalArgumentException("Negocio no encontrado con ID: " + negocioId));
        return citasRepositorio.findByNegocioIdOrderByFechaHoraInicioAsc(negocioId);
    }

    public List<Citas> obtenerCitasPorProfesional(String profesionalId) {
        profesionalRepositorio.findById(profesionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profesional no encontrado con ID: " + profesionalId));
        return citasRepositorio.findByProfesionalIdOrderByFechaHoraInicioAsc(profesionalId);
    }
}
