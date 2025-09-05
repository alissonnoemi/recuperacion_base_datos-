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
}
