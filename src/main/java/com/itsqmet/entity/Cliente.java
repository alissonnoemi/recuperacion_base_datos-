package com.itsqmet.entity;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Document
public class Cliente {

    @MongoId
    private String id;

    @Email(message = "Formato de email inválido")
    private String email;

    private String password;

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @PastOrPresent(message = "La fecha de nacimiento no puede ser en el futuro")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El género es obligatorio")
    private String genero;

    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos")
    private String telefono;
    private List<String> citasIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @Email(message = "Formato de email inválido") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Formato de email inválido") String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotBlank(message = "El nombre completo es obligatorio") String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(@NotBlank(message = "El nombre completo es obligatorio") String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public @NotNull(message = "La fecha de nacimiento es obligatoria") @PastOrPresent(message = "La fecha de nacimiento no puede ser en el futuro") LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(@NotNull(message = "La fecha de nacimiento es obligatoria") @PastOrPresent(message = "La fecha de nacimiento no puede ser en el futuro") LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public @NotBlank(message = "El género es obligatorio") String getGenero() {
        return genero;
    }

    public void setGenero(@NotBlank(message = "El género es obligatorio") String genero) {
        this.genero = genero;
    }

    public @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos") String getTelefono() {
        return telefono;
    }

    public void setTelefono(@Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos") String telefono) {
        this.telefono = telefono;
    }

    public List<String> getCitasIds() {
        return citasIds;
    }

    public void setCitasIds(List<String> citasIds) {
        this.citasIds = citasIds;
    }
}
