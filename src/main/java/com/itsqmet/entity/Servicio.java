package com.itsqmet.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "servicios")
public class Servicio {

    @MongoId
    private String idServicio;

    @NotBlank(message = "El nombre del servicio es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;

    @NotNull(message = "La duración en horas es obligatoria")
    @Min(value = 1, message = "La duración debe ser al menos 1 hora")
    @Max(value = 24, message = "La duración no puede exceder 24 horas")
    private Long duracionHoras;
    private String negocioId;
    private List<String> citasIds;

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public @NotBlank(message = "El nombre del servicio es obligatorio") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre del servicio es obligatorio") String nombre) {
        this.nombre = nombre;
    }

    public @NotNull(message = "El precio es obligatorio") @Min(value = 0, message = "El precio no puede ser negativo") Double getPrecio() {
        return precio;
    }

    public void setPrecio(@NotNull(message = "El precio es obligatorio") @Min(value = 0, message = "El precio no puede ser negativo") Double precio) {
        this.precio = precio;
    }

    public @NotNull(message = "La duración en horas es obligatoria") @Min(value = 1, message = "La duración debe ser al menos 1 hora") @Max(value = 24, message = "La duración no puede exceder 24 horas") Long getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(@NotNull(message = "La duración en horas es obligatoria") @Min(value = 1, message = "La duración debe ser al menos 1 hora") @Max(value = 24, message = "La duración no puede exceder 24 horas") Long duracionHoras) {
        this.duracionHoras = duracionHoras;
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
