package com.itsqmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
public class Citas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(min=3, max=30)
    private String cliente;
    @NotNull(message = "La fecha de la cita es obligatoria")
    @FutureOrPresent(message = "La fecha debe ser hoy o una fecha futura")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCita;
    @NotBlank(message = "El servicio es obligatorio")
    private String servicio;
    @NotBlank(message = "El especialista es obligatorio")

    private String especialista;
}