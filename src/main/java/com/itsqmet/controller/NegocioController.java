package com.itsqmet.controller;

import com.itsqmet.entity.Negocio;
import com.itsqmet.service.CitasServicio;
import com.itsqmet.service.NegocioServicio;
import com.itsqmet.service.ProfesionalServicio;
import com.itsqmet.service.ServicioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class NegocioController {
    @Autowired
    private NegocioServicio negocioServicio;
    @Autowired
    private CitasServicio citasServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private ServicioServicio servicioServicio;

    // Listar todos los negocios
    @GetMapping("/negocios")
    public String listarNegocios(Model model) {
        List<Negocio> negocios = negocioServicio.obtenerTodosLosNegocios();
        if (negocios == null) {
            negocios = Collections.emptyList();
        }
        model.addAttribute("negocios", negocios);
        return "pages/listaNegocio";
    }

    // Mostrar formulario para crear un nuevo negocio
    @GetMapping("/gratis")
    public String mostrarFormularioNuevoNegocio(Model model) {
        model.addAttribute("negocio", new Negocio());
        return "pages/gratis";
    }

    // Procesar el formulario de creación de negocio
    @PostMapping("/guardarNegocio")
    public String guardarNegocio(@Valid @ModelAttribute("negocio") Negocio negocio, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "pages/gratis";
        }
        negocioServicio.guardarNegocio(negocio);
        redirectAttributes.addFlashAttribute("mensajeTipo", "success");
        redirectAttributes.addFlashAttribute("mensajeCuerpo", "Negocio guardado exitosamente!");
        return "redirect:/negocios";
    }

    // Mostrar formulario para editar un negocio
    @GetMapping("/editarNegocio/{id}")
    public String mostrarFormularioEditarNegocio(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Negocio> negocioOptional = negocioServicio.obtenerNegocioPorId(id);
        if (negocioOptional.isPresent()) {
            model.addAttribute("negocio", negocioOptional.get());
            return "pages/gratis";
        } else {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Negocio no encontrado.");
            return "redirect:/negocios";
        }
    }

    // Eliminar un negocio
    @PostMapping("/eliminarNegocio/{id}")
    public String eliminarNegocio(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            negocioServicio.eliminarNegocio(id);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Negocio eliminado exitosamente!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al eliminar el negocio.");
        }
        return "redirect:/negocios";
    }

    // Ver historial de citas de un negocio
    @GetMapping("/historialCitasNegocio/{idNegocio}")
    public String verHistorialCitasNegocio(@PathVariable("idNegocio") Long idNegocio, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Negocio> negocioOptional = negocioServicio.obtenerNegocioPorId(idNegocio);
            if (negocioOptional.isPresent()) {
                Negocio negocio = negocioOptional.get();
                model.addAttribute("negocio", negocio);
                model.addAttribute("citas", citasServicio.obtenerCitasPorNegocio(idNegocio));
                model.addAttribute("profesionales", profesionalServicio.obtenerProfesionalesPorNegocio(idNegocio));
                model.addAttribute("servicios", servicioServicio.obtenerServiciosPorNegocio(idNegocio));
                return "/pages/historialCitasNegocio";
            } else {
                redirectAttributes.addFlashAttribute("mensajeTipo", "error");
                redirectAttributes.addFlashAttribute("mensajeCuerpo", "Negocio no encontrado.");
                return "redirect:/negocios";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al obtener el historial de citas: " + e.getMessage());
            return "redirect:/negocios";
        }
    }
}