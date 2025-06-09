package com.itsqmet.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String nombreCompleto;
    private String emailProfesional;
    private String contrasenia;
    private String nombreNegocio;
    private String tipoNegocio;

}
