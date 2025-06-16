package com.itsqmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNegocio; // Esta es tu clave primaria

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

    private String direccion;
    private String telefono;

    @OneToMany(mappedBy = "negocio", fetch = FetchType.LAZY)
    private List<Profesional> profesionales;

    // Un negocio puede ofrecer muchos servicios
    @OneToMany(mappedBy = "negocio", fetch = FetchType.LAZY)
    private List<Servicio> servicios;

    public Long getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(Long idNegocio) {
        this.idNegocio = idNegocio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmailProfesional() {
        return emailProfesional;
    }

    public void setEmailProfesional(String emailProfesional) {
        this.emailProfesional = emailProfesional;
    }

    public String getNombreNegocio() {
        return nombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        this.nombreNegocio = nombreNegocio;
    }

    public String getTipoNegocio() {
        return tipoNegocio;
    }

    public void setTipoNegocio(String tipoNegocio) {
        this.tipoNegocio = tipoNegocio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Profesional> getProfesionales() {
        return profesionales;
    }

    public void setProfesionales(List<Profesional> profesionales) {
        this.profesionales = profesionales;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
}