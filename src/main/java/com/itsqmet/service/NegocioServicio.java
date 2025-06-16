package com.itsqmet.service;

import com.itsqmet.entity.Cliente;
import com.itsqmet.entity.LogNegocio;
import com.itsqmet.entity.Negocio;
import com.itsqmet.entity.Profesional;
import com.itsqmet.repository.loginNegocioRepositorio;
import com.itsqmet.repository.negocioRepositorio;
import com.itsqmet.repository.profesionalRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NegocioServicio {
@Autowired
    private negocioRepositorio negocioRepositorio;
@Autowired
private profesionalRepositorio profesionalRepositorio;
    public List<Negocio> obtenerTodosLosNegocios() {
        return negocioRepositorio.findAll();
    }

    public Optional<Negocio> obtenerNegocioPorId(Long id) {
        return negocioRepositorio.findById(id);
    }
    @Transactional
    public Negocio guardarNegocio(Negocio negocio) {
        // Puedes añadir validaciones de negocio aquí antes de guardar
        if (negocio.getNombreNegocio() == null || negocio.getNombreNegocio().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del negocio no puede estar vacío.");
        }
        return negocioRepositorio.save(negocio);
    }
    @Transactional
    public Negocio editarNegocio(Long id, Negocio negocioActualizado) {
        Negocio negocioExistente = negocioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Negocio no encontrado con ID: " + id));

        negocioExistente.setNombreCompleto(negocioActualizado.getNombreCompleto());
        negocioExistente.setDireccion(negocioActualizado.getDireccion());
        negocioExistente.setTelefono(negocioActualizado.getTelefono());
        negocioExistente.setEmailProfesional(negocioActualizado.getEmailProfesional());

        return negocioRepositorio.save(negocioExistente);
    }

    public  List <Negocio> mostrarNegocios() {
        return negocioRepositorio.findAll();
    }
    public List <Negocio> buscarNegocioPorNombre(String buscarNegocio) {
        if (buscarNegocio == null || buscarNegocio.isEmpty()) {
            return negocioRepositorio.findAll();
        } else {
            return negocioRepositorio.findBytipoNegocioContainingIgnoreCase(buscarNegocio);
        }
    }
    public Optional<Negocio> buscarNegocioPorId (Long id) {
        return negocioRepositorio.findById(id);
    }
    public Profesional guardarProfesional(Profesional profesional) {
        // Validar que el negocio asociado exista
        if (profesional.getNegocio() == null || profesional.getNegocio().getIdNegocio() == null) {
            throw new IllegalArgumentException("El negocio asociado es obligatorio.");
        }
        Negocio negocio = negocioRepositorio.findById(profesional.getNegocio().getIdNegocio())
                .orElseThrow(() -> new IllegalArgumentException("Negocio no encontrado con ID: " + profesional.getNegocio().getIdNegocio()));

        profesional.setNegocio(negocio); // Asegura que el objeto negocio esté gestionado por JPA
        return profesionalRepositorio.save(profesional);
    }

    @Transactional
    public void eliminarNegocio(Long id) {
        negocioRepositorio.deleteById(id);
    }


}
