package com.itsqmet.entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.List;

@NoArgsConstructor
@Data
@Document(collection = "negocios")
public class Negocio {

    @MongoId
    private String idNegocio;
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre completo no debe exceder 100 caracteres")
    private String nombreCompleto;
    @NotBlank(message = "El email profesional es obligatorio")
    @Email(message = "Debe ingresar un email válido")
    private String emailProfesional;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    @NotBlank(message = "El nombre del negocio es obligatorio")
    private String nombreNegocio;
    @NotBlank(message = "El tipo de negocio es obligatorio")
    private String tipoNegocio;
    private String direccion;
    private String telefono;
    @NotBlank(message = "El RUC es obligatorio")
    @Size(min = 10, max = 13, message = "El RUC debe tener entre 10 y 13 dígitos")
    private String ruc;
    private String plan;
    private List<String> profesionalesIds;
    private List<String> serviciosIds;
}
