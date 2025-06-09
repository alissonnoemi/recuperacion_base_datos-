package com.itsqmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre completo no debe exceder 100 caracteres")
    private String nombreCompleto;
    @NotBlank(message = "El email del profesional es obligatorio")
    @Email(message = "Debe ingresar un email válido")
    private String emailProfesional;
    @NotBlank(message = "El nombre del negocio es obligatorio")
    private String nombreNegocio;
    @NotBlank(message = "El tipo de negocio es obligatorio")
    private String tipoNegocio;

}
