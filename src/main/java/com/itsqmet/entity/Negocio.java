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

    public String getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(String idNegocio) {
        this.idNegocio = idNegocio;
    }

    public @NotBlank(message = "El nombre completo es obligatorio") @Size(max = 100, message = "El nombre completo no debe exceder 100 caracteres") String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(@NotBlank(message = "El nombre completo es obligatorio") @Size(max = 100, message = "El nombre completo no debe exceder 100 caracteres") String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public @NotBlank(message = "El email profesional es obligatorio") @Email(message = "Debe ingresar un email válido") String getEmailProfesional() {
        return emailProfesional;
    }

    public void setEmailProfesional(@NotBlank(message = "El email profesional es obligatorio") @Email(message = "Debe ingresar un email válido") String emailProfesional) {
        this.emailProfesional = emailProfesional;
    }

    public @NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password) {
        this.password = password;
    }

    public @NotBlank(message = "El nombre del negocio es obligatorio") String getNombreNegocio() {
        return nombreNegocio;
    }

    public void setNombreNegocio(@NotBlank(message = "El nombre del negocio es obligatorio") String nombreNegocio) {
        this.nombreNegocio = nombreNegocio;
    }

    public @NotBlank(message = "El tipo de negocio es obligatorio") String getTipoNegocio() {
        return tipoNegocio;
    }

    public void setTipoNegocio(@NotBlank(message = "El tipo de negocio es obligatorio") String tipoNegocio) {
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

    public @NotBlank(message = "El RUC es obligatorio") @Size(min = 10, max = 13, message = "El RUC debe tener entre 10 y 13 dígitos") String getRuc() {
        return ruc;
    }

    public void setRuc(@NotBlank(message = "El RUC es obligatorio") @Size(min = 10, max = 13, message = "El RUC debe tener entre 10 y 13 dígitos") String ruc) {
        this.ruc = ruc;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public List<String> getProfesionalesIds() {
        return profesionalesIds;
    }

    public void setProfesionalesIds(List<String> profesionalesIds) {
        this.profesionalesIds = profesionalesIds;
    }

    public List<String> getServiciosIds() {
        return serviciosIds;
    }

    public void setServiciosIds(List<String> serviciosIds) {
        this.serviciosIds = serviciosIds;
    }
}
