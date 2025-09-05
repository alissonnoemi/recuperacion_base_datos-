package com.itsqmet.entity;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;


@Document
@Data
public class Citas {
    @MongoId
    private String idCita;

    @NotNull(message = "La fecha y hora de inicio de la cita es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @FutureOrPresent(message = "La fecha y hora de inicio debe ser en el presente o futuro")
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "La fecha y hora de fin de la cita es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaHoraFin;

    @Min(value = 1, message = "La duración del servicio debe ser al menos 1 hora")
    @Max(value = 24, message = "La duración del servicio no puede exceder 24 horas")
    private Long duracionServicioHoras;

    private EstadoCita estadoCita;

    private String clienteId;
    private String servicioId;
    private String profesionalId;
    private String negocioId;

    public enum EstadoCita {
        PENDIENTE, CONFIRMADA, COMPLETADA, CANCELADA, REPROGRAMADA
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public @NotNull(message = "La fecha y hora de inicio de la cita es obligatoria") @FutureOrPresent(message = "La fecha y hora de inicio debe ser en el presente o futuro") LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(@NotNull(message = "La fecha y hora de inicio de la cita es obligatoria") @FutureOrPresent(message = "La fecha y hora de inicio debe ser en el presente o futuro") LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public @NotNull(message = "La fecha y hora de fin de la cita es obligatoria") LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(@NotNull(message = "La fecha y hora de fin de la cita es obligatoria") LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public @Min(value = 1, message = "La duración del servicio debe ser al menos 1 hora") @Max(value = 24, message = "La duración del servicio no puede exceder 24 horas") Long getDuracionServicioHoras() {
        return duracionServicioHoras;
    }

    public void setDuracionServicioHoras(@Min(value = 1, message = "La duración del servicio debe ser al menos 1 hora") @Max(value = 24, message = "La duración del servicio no puede exceder 24 horas") Long duracionServicioHoras) {
        this.duracionServicioHoras = duracionServicioHoras;
    }

    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getServicioId() {
        return servicioId;
    }

    public void setServicioId(String servicioId) {
        this.servicioId = servicioId;
    }

    public String getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(String profesionalId) {
        this.profesionalId = profesionalId;
    }

    public String getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(String negocioId) {
        this.negocioId = negocioId;
    }
}
