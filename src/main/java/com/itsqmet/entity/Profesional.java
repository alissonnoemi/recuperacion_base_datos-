package com.itsqmet.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "profesionales")
public class Profesional {
    @MongoId
    private String idProfesional;
    @NotBlank(message = "El nombre del profesional es obligatorio")
    private String nombreCompleto;
    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos")
    private String telefono;
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Formato de email inválido"
    )
    private String email;
    private String negocioId;
    private List<String> citasIds;

    public String getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(String idProfesional) {
        this.idProfesional = idProfesional;
    }

    public @NotBlank(message = "El nombre del profesional es obligatorio") String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(@NotBlank(message = "El nombre del profesional es obligatorio") String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public @NotBlank(message = "La especialidad es obligatoria") String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(@NotBlank(message = "La especialidad es obligatoria") String especialidad) {
        this.especialidad = especialidad;
    }

    public @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos") String getTelefono() {
        return telefono;
    }

    public void setTelefono(@Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos") String telefono) {
        this.telefono = telefono;
    }

    public @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Formato de email inválido"
    ) String getEmail() {
        return email;
    }

    public void setEmail(@Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Formato de email inválido"
    ) String email) {
        this.email = email;
    }

    public String getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(String negocioId) {
        this.negocioId = negocioId;
    }

    public List<String> getCitasIds() {
        return citasIds;
    }

    public void setCitasIds(List<String> citasIds) {
        this.citasIds = citasIds;
    }
}
