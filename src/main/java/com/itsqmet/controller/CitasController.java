package com.itsqmet.controller;

import com.itsqmet.entity.Citas;
import com.itsqmet.entity.Cliente;
import com.itsqmet.entity.Profesional;
import com.itsqmet.entity.Servicio;
import com.itsqmet.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Controller
public class CitasController {

    @Autowired
    private CitasServicio citasServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private ServicioServicio servicioServicio;
    @Autowired
    private NegocioServicio negocioServicio;

    @GetMapping("/listaCita")
    public String mostrarListaCitas(Model model) {
        model.addAttribute("citas", citasServicio.obtenerTodosLosCitas());
        return "pages/listaCita";
    }
    @GetMapping("/agendar")
    public String mostrarFormularioAgendar(Model model) {
        model.addAttribute("cita", new Citas());
        model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
        model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
        model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
        return "pages/cita";
    }
    @PostMapping("/agendar")
    public String agendarCita(@Valid @ModelAttribute("cita") Citas cita,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
            return "pages/listaCita";
        }
        try {
            citasServicio.agendarNuevaCita(cita);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Cita agendada exitosamente!");
            return "redirect:/listaCita";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", e.getMessage());
            model.addAttribute("cita", cita);
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
            return "pages/cita";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al agendar la cita: " + e.getMessage());
            model.addAttribute("cita", cita);
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
            return "pages/cita";
        }
    }

    // Mostrar formulario de edición (GET)
    @GetMapping("/editarCita/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Optional<Citas> citaOpt = citasServicio.buscarCitaPorId(id);
        if (citaOpt.isPresent()) {
            model.addAttribute("cita", citaOpt.get());
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
            return "pages/cita";
        } else {
            return "redirect:/listaCita";
        }
    }

    @PostMapping("/editarCita/{id}")
    public String actualizarCita(@PathVariable("id") Long id,
                                 @Valid @ModelAttribute("cita") Citas cita,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
            return "pages/cita";
        }
        return "redirect:/listaCita";
    }
    @GetMapping("/eliminarCita/{id}")
    public String eliminarCita(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            citasServicio.eliminarCita(id);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Cita eliminada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al eliminar la cita: " + e.getMessage());
        }
        return "redirect:/listaCita";
    }
    @GetMapping("/cita")
    public String mostrarFormularioCita(Model model) {
        model.addAttribute("cita", new Citas());
        model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
        model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
        model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
        return "pages/cita";
    }
}