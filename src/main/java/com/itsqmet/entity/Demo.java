package com.itsqmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Demo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 10, message = "El nombre no debe exceder 10 caracteres")
    private String firstName;
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 10, message = "El apellido no debe exceder 10 caracteres")
    private String lastName;
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener exactamente 10 dígitos numéricos")
    private String phone;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ingresar un email válido")
    @Size(max = 100, message = "El email no debe exceder 100 caracteres")
    private String email;
    @NotBlank(message = "El nombre del negocio es obligatorio")
    @Size(max = 100, message = "El nombre del negocio no debe exceder 100 caracteres")
    private String businessName;
    @NotBlank(message = "El tipo de negocio es obligatorio")
    @Size(max = 50, message = "El tipo de negocio no debe exceder 50 caracteres")
    private String businessType;
    @NotBlank(message = "El tamaño del equipo es obligatorio")
    private String teamSize;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
