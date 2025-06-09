package com.itsqmet.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LogNegocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nombreNegocio;
    private String email;
    private String password;
    private String tipoNegocio;
    private String direccion;
    private String telefono;




}
