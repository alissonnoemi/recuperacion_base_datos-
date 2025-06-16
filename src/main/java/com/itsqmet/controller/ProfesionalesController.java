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
public class ProfesionalesController {
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private NegocioServicio negocioServicio;
    @Autowired
    private CitasServicio citasServicio;

    // Listar todos los profesionales
    @GetMapping("/listaProfesionales")
    public String listarProfesionales(Model model) {
        model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
        return "pages/listaProfesionales";
    }

    // Mostrar formulario para crear un nuevo profesional
    @GetMapping("/profesionales")
    public String mostrarFormularioNuevoProfesional(Model model) {
        Profesional nuevoProfesional = new Profesional();
        nuevoProfesional.setNegocio(new Negocio());
        model.addAttribute("profesional", nuevoProfesional);
        model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
        return "pages/profesionales";
    }

    // Procesar el formulario de creación de profesional
    @PostMapping("/guardarProfesionales")
    public String guardarProfesional(@Valid @ModelAttribute("profesional") Profesional profesional, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales";
        }
        try {
            profesionalServicio.guardarProfesional(profesional);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional guardado exitosamente!");
            return "redirect:/listaProfesionales";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", e.getMessage());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "papes/profesionales";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al guardar el profesional: " + e.getMessage());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales";
        }
    }
    // Mostrar formulario para editar un profesional
    @GetMapping("/editarProfesional/{id}")
    public String mostrarFormularioEditarProfesional(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Profesional> profesionalOptional = profesionalServicio.obtenerProfesionalPorId(id);
        if (profesionalOptional.isPresent()) {
            model.addAttribute("profesional", profesionalOptional.get());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "/pages/profesionales";
        } else {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional no encontrado para editar.");
            return "redirect:/listaProfesionales";
        }
    }


    // Eliminar un profesional
    @PostMapping("/eliminarProfesional/{id}")
    public String eliminarProfesional(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        profesionalServicio.eliminarProfesional(id);
        redirectAttributes.addFlashAttribute("mensajeTipo", "success");
        redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional eliminado exitosamente!");
        return "redirect:/listaProfesionales";
    }

    // Ver historial de citas de un profesional
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