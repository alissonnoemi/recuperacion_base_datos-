package com.itsqmet.service;

import com.itsqmet.entity.Negocio;
import com.itsqmet.entity.Servicio;
import com.itsqmet.repository.negocioRepositorio;
import com.itsqmet.repository.servicioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServicio {

    @Autowired
    private servicioRepositorio servicioRepositorio;
    @Autowired
    private negocioRepositorio negocioRepositorio; // Para validar el negocio asociado

    public List<Servicio> obtenerTodosLosServicios() {
        return servicioRepositorio.findAll();
    }

    public Optional<Servicio> obtenerServicioPorId(Long id) {
        return servicioRepositorio.findById(id);
    }

    @Transactional
    public Servicio guardarServicio(Servicio servicio) {
        // Validar que el negocio asociado exista
        if (servicio.getNegocio() == null || servicio.getNegocio().getIdNegocio() == null) {
            throw new IllegalArgumentException("El negocio asociado es obligatorio para el servicio.");
        }
        Negocio negocio = negocioRepositorio.findById(servicio.getNegocio().getIdNegocio())
                .orElseThrow(() -> new IllegalArgumentException("Negocio no encontrado con ID: " + servicio.getNegocio().getIdNegocio()));

        servicio.setNegocio(negocio);
        return servicioRepositorio.save(servicio);
    }

    @Transactional
    public Servicio actualizarServicio(Long id, Servicio servicioActualizado) {
        Servicio servicioExistente = servicioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + id));

        servicioExistente.setNombre(servicioActualizado.getNombre());
        servicioExistente.setPrecio(servicioActualizado.getPrecio());
        servicioExistente.setDuracionHoras(servicioActualizado.getDuracionHoras());

        // Si se actualiza el negocio
        if (servicioActualizado.getNegocio() != null && servicioActualizado.getNegocio().getIdNegocio() != null) {
            Negocio nuevoNegocio = negocioRepositorio.findById(servicioActualizado.getNegocio().getIdNegocio())
                    .orElseThrow(() -> new IllegalArgumentException("Nuevo Negocio no encontrado con ID: " + servicioActualizado.getNegocio().getIdNegocio()));
            servicioExistente.setNegocio(nuevoNegocio);
        }

        return servicioRepositorio.save(servicioExistente);
    }

    public void eliminarServicio(Long id) {
        if (!servicioRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Servicio no encontrado con ID: " + id);
        }
        servicioRepositorio.deleteById(id);
    }

    public List<Servicio> obtenerServiciosPorNegocio(Long idNegocio) {
        return servicioRepositorio.findByNegocio_IdNegocio(idNegocio);
    }
}
