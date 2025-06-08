package com.itsqmet.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(unique = true)
    private String nombrePlan;
    private String descripcion;
    private BigDecimal precioMensual;
}