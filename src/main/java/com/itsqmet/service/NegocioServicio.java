package com.itsqmet.service;

import com.itsqmet.entity.Negocio;
import com.itsqmet.entity.Profesional;
import com.itsqmet.repository.negocioRepositorio;
import com.itsqmet.repository.profesionalRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public  Optional<Negocio> obtenerNegocioPorEmail(String emailProfesional) {
        return negocioRepositorio.findByEmailProfesional(emailProfesional);
    }
    public Optional<Negocio> obtenerNegocioPorRuc(String ruc) {
        return negocioRepositorio.findByRuc(ruc);
    }
    @Transactional
    public Negocio guardarNegocio(Negocio negocio) {
        return negocioRepositorio.save(negocio);
    }
    public Optional<Negocio> findByEmail(String emailProfesional) {
        return negocioRepositorio.findByEmailProfesional(emailProfesional);
    }
    public boolean validarCredenciales(String emailProfesional, String password) {
        List<Negocio> negocios = negocioRepositorio.findByTipoNegocioContainingIgnoreCase(emailProfesional);
        if (negocios.isEmpty()) return false;
        Negocio negocio = negocios.get(0);
        // Asegúrate de tener el campo password en la entidad Negocio
        return negocio.getPassword() != null && negocio.getPassword().equals(password);
    }
    @Transactional
    public Negocio editarNegocio(Long id, Negocio negocioActualizado) {
        Negocio negocioExistente = negocioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Negocio no encontrado con ID: " + id));

        negocioExistente.setNombreCompleto(negocioActualizado.getNombreCompleto());
        negocioExistente.setDireccion(negocioActualizado.getDireccion());
        negocioExistente.setTelefono(negocioActualizado.getTelefono());
        negocioExistente.setTipoNegocio(negocioActualizado.getTipoNegocio());
        if (!negocioExistente.getEmailProfesional().equals(negocioActualizado.getEmailProfesional())) {
            if (isEmailAlreadyRegistered(negocioActualizado.getEmailProfesional())) {
                throw new IllegalArgumentException("El nuevo email profesional ya está registrado por otro negocio.");
            }
            negocioExistente.setEmailProfesional(negocioActualizado.getEmailProfesional());
        }
        if (!negocioExistente.getRuc().equals(negocioActualizado.getRuc())) {
            if (isRucAlreadyRegistered(negocioActualizado.getRuc())) {
                throw new IllegalArgumentException("El nuevo RUC ya está registrado por otro negocio.");
            }
            String rucValidationMessage = validarRucParaRegistro(negocioActualizado.getRuc());
            if (rucValidationMessage != null) {
                throw new IllegalArgumentException(rucValidationMessage);
            }
            negocioExistente.setRuc(negocioActualizado.getRuc());
        }
        negocioExistente.setPlan(negocioActualizado.getPlan());
        return negocioRepositorio.save(negocioExistente);
    }
    public boolean isRucAlreadyRegistered(String ruc) {
        return negocioRepositorio.findByRuc(ruc).isPresent();
    }
    public boolean isEmailAlreadyRegistered(String emailProfesional) {
        return negocioRepositorio.findByEmailProfesional(emailProfesional).isPresent();
    }
    public String validarRucParaRegistro(String ruc) {
        // Validación básica de longitud y formato (ya cubierto por @Pattern en DTO, pero lo reforzamos)
        if (ruc == null || ruc.length() < 10 || ruc.length() > 13 || !ruc.matches("^\\d{10}(\\d{3})?$")) {
            return "Formato de RUC inválido (debe tener 10 o 13 dígitos numéricos).";
        }
        if (ruc.equals("1234567890001")) { // Ejemplo de RUC "ilegal"
            return "El RUC 1234567890001 ha sido marcado como ilegal.";
        }
        if (ruc.endsWith("999")) { // Ejemplo de RUC "inválido" (no termina en 001, etc.)
            return "El RUC no cumple con las terminaciones estándar (ej. 001, etc.).";
        }
        return null; // Si el RUC pasa todas las validaciones simuladas
    }
    public List<Negocio> buscarNegocioPorNombre(String buscarNegocio) {
        if (buscarNegocio == null || buscarNegocio.isEmpty()) {
            return negocioRepositorio.findAll();
        } else {
            return negocioRepositorio.findByTipoNegocioContainingIgnoreCase(buscarNegocio);
        }
    }

    public Optional<Negocio> buscarNegocioPorId(Long id) {
        return negocioRepositorio.findById(id);
    }
    @Transactional
    public Profesional guardarProfesional(Profesional profesional) {
        if (profesional.getNegocio() == null || profesional.getNegocio().getIdNegocio() == null) {
            throw new IllegalArgumentException("El negocio asociado es obligatorio.");
        }
        Negocio negocio = negocioRepositorio.findById(profesional.getNegocio().getIdNegocio())
                .orElseThrow(() -> new IllegalArgumentException("Negocio no encontrado con ID: " + profesional.getNegocio().getIdNegocio()));

        profesional.setNegocio(negocio);
        return profesionalRepositorio.save(profesional);
    }
    @Transactional
    public void eliminarNegocio(Long id) {
        negocioRepositorio.deleteById(id);
    }
}