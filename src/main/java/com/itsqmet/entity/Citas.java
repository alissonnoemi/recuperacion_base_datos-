package com.itsqmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Citas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;
    @NotNull(message = "La fecha y hora de inicio de la cita es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @FutureOrPresent(message = "La fecha y hora de inicio debe ser en el presente o futuro")
    private LocalDateTime fechaHoraInicio;
    @NotNull(message = "La fecha y hora de fin de la cita es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaHoraFin;
    @Min(value = 1, message = "La duración del servicio debe ser al menos 1 minuto")
    @Max(value = 1440, message = "La duración del servicio no puede exceder 1440 minutos (24 horas)")
    private Long duracionServicioMinutos;
    @Column(name = "estado_cita", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCita estadoCita;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_profesional", nullable = false)
    private Profesional profesional;
    public enum EstadoCita {
        PENDIENTE, CONFIRMADA, COMPLETADA, CANCELADA, REPROGRAMADA
    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public Long getDuracionServicioMinutos() {
        return duracionServicioMinutos;
    }

    public void setDuracionServicioMinutos(Long duracionServicioMinutos) {
        this.duracionServicioMinutos = duracionServicioMinutos;
    }

    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }
}