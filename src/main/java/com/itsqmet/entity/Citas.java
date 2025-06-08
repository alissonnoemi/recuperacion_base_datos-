package com.itsqmet.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
public class Citas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(unique = true)
    private String cliente;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCita;
    private String servicio;
    private String especialista;

}
