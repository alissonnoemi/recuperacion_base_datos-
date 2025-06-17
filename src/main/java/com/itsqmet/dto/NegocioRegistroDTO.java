package com.itsqmet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data // Lombok para getters, setters, toString, equals, hashCode
public class NegocioRegistroDTO {

    // Campos de la entidad Negocio
    private Long idNegocio; // Para edición, si se reutiliza el formulario

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

    @NotBlank(message = "El plan del negocio es obligatorio")
    private String plan; // "basico", "pro", "empresarial"

    // Campos de datos de pago
    // Ahora son SIEMPRE obligatorios en este formulario, ya que el plan se elige desde el inicio.
    @NotBlank(message = "El número de tarjeta es obligatorio")
    @Pattern(regexp = "^[0-9]{13,16}$", message = "Número de tarjeta inválido (13-16 dígitos)")
    private String cardNumber;

    @NotBlank(message = "La fecha de vencimiento es obligatoria")
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/[0-9]{2}$", message = "Formato de fecha de vencimiento inválido (MM/AA)")
    private String expiryDate;

    @NotBlank(message = "El CVV es obligatorio")
    @Pattern(regexp = "^[0-9]{3,4}$", message = "CVV inválido (3-4 dígitos)")
    private String cvv;

    @NotBlank(message = "La dirección de facturación es obligatoria")
    private String billingAddress;


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

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
}