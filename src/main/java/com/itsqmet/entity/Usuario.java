package com.itsqmet.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(unique = true)
    private String nombre;
    private String email;
    private String password;
    private String telefono;


}