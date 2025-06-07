package com.itsqmet.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(unique = true)
    private String remitente;
    private String email;
    private String mensaje;;
}
