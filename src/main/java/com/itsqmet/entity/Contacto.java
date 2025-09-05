package com.itsqmet.entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Data
public class Contacto {
    @MongoId
    private Long id;
    @NotBlank (message = "El nombre es obligatorio")
    @Size(max = 30, message = "El nombre no debe exceder 30 caracteres")
    private String name;
    @NotBlank (message = "El email es obligatorio")
    @Size(max = 50, message = "El email no debe exceder 50 caracteres")
    @jakarta.validation.constraints.Email(message = "Debe ingresar un email válido")
    private String email;
    private String subject;
    @NotBlank (message = "El mensaje es obligatorio")
    @Size(max = 200, message = "El mensaje no debe exceder 200 caracteres")
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
