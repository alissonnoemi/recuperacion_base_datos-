package com.itsqmet.controller;

import com.itsqmet.entity.Citas;
import com.itsqmet.entity.Servicio;
import com.itsqmet.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        Citas cita = new Citas();
        cita.setDuracionServicioMinutos(0L);
        model.addAttribute("cita", cita);
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
            return "pages/cita";
        }
        try {
            citasServicio.agendarNuevaCita(cita);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Cita agendada exitosamente!");
            return "redirect:/listaCita";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", e.getMessage());
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
            return "pages/cita";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al agendar la cita: " + e.getMessage());
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
            return "pages/cita";
        }
    }

    @GetMapping("/editarCita/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Optional<Citas> citaOpt = citasServicio.buscarCitaPorId(id);
        if (citaOpt.isPresent()) {
            Citas cita = citaOpt.get();
            model.addAttribute("cita", cita);
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
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios()); // Corregido el typo aquí
            return "pages/cita";
        }
        try {
            cita.setIdCita(id);
            citasServicio.actualizarCita(cita.getIdCita(), cita);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Cita actualizada exitosamente!");
            return "redirect:/listaCita";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", e.getMessage());
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios()); // Corregido el typo aquí
            return "pages/cita";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al actualizar la cita: " + e.getMessage());
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
            return "pages/cita";
        }
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
}