package com.itsqmet.controller;

import com.itsqmet.entity.Negocio;
import com.itsqmet.entity.Profesional;
import com.itsqmet.service.CitasServicio;
import com.itsqmet.service.NegocioServicio;
import com.itsqmet.service.ProfesionalServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ProfesionalesController { // Renombrado de ProfesionalesController a ProfesionalController (singular es más común)
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private NegocioServicio negocioServicio;
    @Autowired
    private CitasServicio citasServicio;

    // --- Listar todos los profesionales ---
    @GetMapping("/listaProfesionales")
    public String listarProfesionales(Model model) {
        model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
        return "pages/listaProfesionales";
    }

    // --- Mostrar formulario para CREAR un nuevo profesional ---
    // Este endpoint es el que usará el botón "Crear Nuevo Profesional"
    @GetMapping("/crearProfesional") // CAMBIO DE ENDPOINT
    public String mostrarFormularioCrearProfesional(Model model) {
        Profesional nuevoProfesional = new Profesional();
        // Es buena práctica inicializar el Negocio para evitar NullPointerException en el formulario
        // si el profesional no tiene un negocio asociado inicialmente.
        if (nuevoProfesional.getNegocio() == null) {
            nuevoProfesional.setNegocio(new Negocio());
        }
        model.addAttribute("profesional", nuevoProfesional);
        model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
        return "pages/profesionales"; // Reutiliza el mismo formulario
    }

    // --- Procesar el formulario de CREACIÓN de profesional ---
    // Este @PostMapping maneja las solicitudes del formulario cuando se está CREANDO un nuevo profesional.
    @PostMapping("/crearProfesional") // CAMBIO DE ENDPOINT (Coincide con th:action cuando idProfesional es null)
    public String crearProfesional(@Valid @ModelAttribute("profesional") Profesional profesional, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales"; // Regresa al formulario con errores
        }
        try {
            profesionalServicio.guardarProfesional(profesional);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional creado exitosamente!");
            return "redirect:/listaProfesionales";
        } catch (Exception e) { // Se usa Exception genérica para capturar cualquier error
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al crear el profesional: " + e.getMessage());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales"; // Regresa al formulario con el error
        }
    }

    // --- Mostrar formulario para EDITAR un profesional ---
    // Este endpoint es el que usará el botón "Editar" en la lista.
    @GetMapping("/editarProfesional/{id}")
    public String mostrarFormularioEditarProfesional(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Profesional> profesionalOptional = profesionalServicio.obtenerProfesionalPorId(id);
        if (profesionalOptional.isPresent()) {
            model.addAttribute("profesional", profesionalOptional.get());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales"; // Usa el mismo formulario
        } else {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional no encontrado para editar.");
            return "redirect:/listaProfesionales";
        }
    }

    // --- Procesar el formulario de ACTUALIZACIÓN de profesional ---
    // ESTE ES EL NUEVO @PostMapping que necesitas para manejar las actualizaciones.
    @PostMapping("/editarProfesional/{id}") // Coincide con th:action cuando idProfesional NO es null
    public String actualizarProfesional(@PathVariable("id") Long id,
                                        @Valid @ModelAttribute("profesional") Profesional profesional,
                                        BindingResult result,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales"; // Regresa al formulario con errores
        }
        try {
            // Aseguramos que el ID del profesional sea el del path variable, crucial para la actualización
            profesional.setIdProfesional(id); // Asumiendo que el campo ID en Profesional es 'idProfesional'
            profesionalServicio.guardarProfesional(profesional); // Este método debe manejar la actualización
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional actualizado exitosamente!");
            return "redirect:/listaProfesionales";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al actualizar el profesional: " + e.getMessage());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales"; // Regresa al formulario con el error
        }
    }


    // --- Eliminar un profesional (Mantenemos tu @PostMapping, aunque un @GetMapping simple es más común para eliminar desde enlaces) ---
    // ¡OJO! Si eliminas desde un enlace simple (<a href="...">), DEBERÍA SER UN @GETMapping.
    // Si es un botón en un formulario (incluso pequeño), @PostMapping está bien.
    @PostMapping("/eliminarProfesional/{id}")
    public String eliminarProfesional(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try { // Añadimos un try-catch para manejar errores de eliminación
            profesionalServicio.eliminarProfesional(id);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional eliminado exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al eliminar el profesional: " + e.getMessage());
        }
        return "redirect:/listaProfesionales";
    }

    // --- Ver historial de citas de un profesional ---
    @GetMapping("/historialCitas/{idProfesional}")
    public String verHistorialCitasProfesional(@PathVariable("idProfesional") Long idProfesional, Model model, RedirectAttributes redirectAttributes) {
        Optional<Profesional> profesionalOptional = profesionalServicio.obtenerProfesionalPorId(idProfesional);
        if (profesionalOptional.isPresent()) {
            Profesional profesional = profesionalOptional.get();
            model.addAttribute("profesional", profesional);
            model.addAttribute("citas", citasServicio.obtenerCitasPorProfesional(idProfesional));
            return "pages/historialProfesional";
        } else {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional no encontrado.");
            return "redirect:/listaProfesionales";
        }
    }
}