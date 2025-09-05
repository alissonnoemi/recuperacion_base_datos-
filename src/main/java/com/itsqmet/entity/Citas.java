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
}
