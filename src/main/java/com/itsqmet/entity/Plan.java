package com.itsqmet.entity;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "planes")
public class Plan {

    @MongoId
    private String id;
    @Indexed(unique = true)
    private String nombre;
    private String descripcion;
    private Double precio;
}
