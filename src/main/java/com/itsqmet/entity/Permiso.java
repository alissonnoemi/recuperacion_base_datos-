package com.itsqmet.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nombre;
    private String descripcion;
}