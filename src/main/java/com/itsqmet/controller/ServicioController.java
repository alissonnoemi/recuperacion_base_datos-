package com.itsqmet.controller;

import com.itsqmet.entity.Negocio;
import com.itsqmet.entity.Servicio;
import com.itsqmet.service.NegocioServicio;
import com.itsqmet.service.ServicioServicio;
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
public class ServicioController {
    @Autowired
    private ServicioServicio servicioServicio;
    @Autowired
    private NegocioServicio negocioServicio;

    // Listar todos los servicios
    @GetMapping("/listaServicios")
    public String listarServicios(Model model) {
        model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
        return "pages/listaServicios";
    }

    // Mostrar formulario para crear un nuevo servicio
    @GetMapping("/registrarServicios")
    public String mostrarFormularioNuevoServicio(Model model) {
        Servicio nuevoServicio = new Servicio();
        nuevoServicio.setNegocio(new Negocio());
        model.addAttribute("servicio", nuevoServicio);
        model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
        return "pages/registrarServicios";
    }

    // Procesar el formulario de creación de servicio
    @PostMapping("/guardarServicio")
    public String guardarServicio(@Valid @ModelAttribute("servicio") Servicio servicio, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/registrarServicios";
        }
        servicioServicio.guardarServicio(servicio);
        return "redirect:/listaServicios";
    }

    // Mostrar formulario para editar un servicio
    @GetMapping("/editarServicio/{id}")
    public String mostrarFormularioEditarServicio(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Servicio> servicioOptional = servicioServicio.obtenerServicioPorId(id);
        if (servicioOptional.isPresent()) {
            model.addAttribute("servicio", servicioOptional.get());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/registrarServicios";
        } else {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Servicio no encontrado para editar.");
            return "redirect:/listaServicios";
        }
    }

    // Eliminar un servicio
    @PostMapping("/eliminarServicio/{id}")
    public String eliminarServicio(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        servicioServicio.eliminarServicio(id);
        return "redirect:/listaServicios";
    }
}