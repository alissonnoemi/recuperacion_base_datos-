package com.itsqmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
@Data
@NoArgsConstructor
@Entity
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;
    @NotBlank(message = "El nombre del servicio es obligatorio")
    private String nombre;
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;
    @NotNull(message = "La duración en horas es obligatoria")
    @Min(value = 1, message = "La duración debe ser al menos 1 hora")
    @Max(value = 24, message = "La duración no puede exceder 24 horas")
    private Long duracionHoras;
    // Muchos servicios pertenecen a un Negocio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_negocio", nullable = false)
    private Negocio negocio;
    // Un servicio puede ser parte de muchas citas
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Citas> citas;

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(Long duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    public List<Citas> getCitas() {
        return citas;
    }

    public void setCitas(List<Citas> citas) {
        this.citas = citas;
    }
}
