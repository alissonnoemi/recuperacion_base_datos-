package com.itsqmet.service;

import com.itsqmet.entity.Citas;
import com.itsqmet.repository.citasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitasServicio {
@Autowired
private citasRepositorio citasRepositorio;
    public List<Citas> obtenerTodosLosCitas() {
        return citasRepositorio.findAll();
    }
    // En CitasServicio.java
    public List<Citas> listarCitas() {
        return citasRepositorio.findAll();
    }
    //  buscar
    public List<Citas> buscarCitaPorEspecialista (String buscarEspecialista) {
        if (buscarEspecialista == null || buscarEspecialista.isEmpty()) {
            return citasRepositorio.findAll();
        }else {
            return citasRepositorio.findByEspecialistaContainingIgnoreCase(buscarEspecialista);
        }
    }
    public Optional<Citas> buscarCitaPorId(Long id) {
        return citasRepositorio.findById(id);
    }
    //  guardar contacto:
    public Citas guardarCita(Citas citas) {
        return citasRepositorio.save(citas);
    }
    //  eliminar contacto:
    public void eliminarCita(Long id) {
        citasRepositorio.deleteById(id);
    }
}
