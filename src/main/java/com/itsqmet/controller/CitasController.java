package com.itsqmet.controller;

import com.itsqmet.entity.Citas;
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

import java.util.Collections;
import java.util.List;
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
        cita.setDuracionServicioHoras(0L);
        model.addAttribute("cita", cita);
        model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
        model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
        model.addAttribute("profesionales", Collections.emptyList());
        model.addAttribute("servicios", Collections.emptyList());
        return "pages/cita";
    }

    @PostMapping("/agendar")
    public String agendarCita(@Valid @ModelAttribute("cita") Citas cita,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        Runnable addCommonModelAttributes = () -> {
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            if (cita.getNegocioId() != null) {
                model.addAttribute("profesionales", profesionalServicio.obtenerProfesionalesPorNegocio(cita.getNegocioId()));
                model.addAttribute("servicios", servicioServicio.obtenerServiciosPorNegocio(cita.getNegocioId()));
            } else {
                model.addAttribute("profesionales", Collections.emptyList());
                model.addAttribute("servicios", Collections.emptyList());
            }
        };

        if (result.hasErrors()) {
            addCommonModelAttributes.run();
            return "pages/cita";
        }
        try {
            citasServicio.agendarNuevaCita(cita);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Cita agendada exitosamente!");
            return "redirect:/listaCita";
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensajeTipo", "error");
            model.addAttribute("mensajeCuerpo", e.getMessage());
            addCommonModelAttributes.run();
            return "pages/cita";
        } catch (Exception e) {
            model.addAttribute("mensajeTipo", "error");
            model.addAttribute("mensajeCuerpo", "Error al agendar la cita: " + e.getMessage());
            addCommonModelAttributes.run();
            return "pages/cita";
        }
    }

    @GetMapping("/editarCita/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") String id, Model model) {
        Optional<Citas> citaOpt = citasServicio.buscarCitaPorId(id);
        if (citaOpt.isPresent()) {
            Citas cita = citaOpt.get();
            model.addAttribute("cita", cita);
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            model.addAttribute("profesionales", Collections.emptyList());
            model.addAttribute("servicios", Collections.emptyList());
            return "pages/cita";
        } else {
            model.addAttribute("mensajeTipo", "error");
            model.addAttribute("mensajeCuerpo", "Cita no encontrada para edición.");
            return "redirect:/listaCita";
        }
    }

    @PostMapping("/editarCita/{id}")
    public String actualizarCita(@PathVariable("id") String id,
                                 @Valid @ModelAttribute("cita") Citas cita,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        Runnable addCommonModelAttributes = () -> {
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            if (cita.getNegocioId() != null) {
                model.addAttribute("profesionales", profesionalServicio.obtenerProfesionalesPorNegocio(cita.getNegocioId()));
                model.addAttribute("servicios", servicioServicio.obtenerServiciosPorNegocio(cita.getNegocioId()));
            } else {
                model.addAttribute("profesionales", Collections.emptyList());
                model.addAttribute("servicios", Collections.emptyList());
            }
        };

        if (result.hasErrors()) {
            addCommonModelAttributes.run();
            return "pages/cita";
        }
        try {
            cita.setIdCita(id);
            citasServicio.actualizarCita(id, cita);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Cita actualizada exitosamente!");
            return "redirect:/listaCita";
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensajeTipo", "error");
            model.addAttribute("mensajeCuerpo", e.getMessage());
            addCommonModelAttributes.run();
            return "pages/cita";
        } catch (Exception e) {
            model.addAttribute("mensajeTipo", "error");
            model.addAttribute("mensajeCuerpo", "Error al actualizar la cita: " + e.getMessage());
            addCommonModelAttributes.run();
            return "pages/cita";
        }
    }

    @GetMapping("/eliminarCita/{id}")
    public String eliminarCita(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
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

    @GetMapping("/api/profesionales/porNegocio/{negocioId}")
    @ResponseBody
    public List<Profesional> getProfesionalesPorNegocio(@PathVariable("negocioId") String negocioId) {
        return profesionalServicio.obtenerProfesionalesPorNegocio(negocioId);
    }

    @GetMapping("/api/servicios/porNegocio/{negocioId}")
    @ResponseBody
    public List<Servicio> getServiciosPorNegocio(@PathVariable("negocioId") String negocioId) {
        return servicioServicio.obtenerServiciosPorNegocio(negocioId);
    }
}
