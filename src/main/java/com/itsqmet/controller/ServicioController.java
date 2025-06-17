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

    @GetMapping("/listaServicios")
    public String listarServicios(Model model) {
        model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
        return "pages/listaServicios";
    }

    @GetMapping("/crearServicio")
    public String mostrarFormularioNuevoServicio(Model model) {
        Servicio nuevoServicio = new Servicio();
        if (nuevoServicio.getNegocio() == null) {
            nuevoServicio.setNegocio(new Negocio());
        }
        model.addAttribute("servicio", nuevoServicio);
        model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
        return "pages/registrarServicios";
    }

    @PostMapping("/crearServicio")
    public String crearServicio(@Valid @ModelAttribute("servicio") Servicio servicio, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/registrarServicios"; // Regresa al formulario con los errores
        }
        try {
            servicioServicio.guardarServicio(servicio); // Asumiendo que este método guarda si ID es null
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Servicio creado exitosamente!");
            return "redirect:/listaServicios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al crear el servicio: " + e.getMessage());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/registrarServicios";
        }
    }

    @GetMapping("/editarServicio/{id}")
    public String mostrarFormularioEditarServicio(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Servicio> servicioOptional = servicioServicio.obtenerServicioPorId(id);
        if (servicioOptional.isPresent()) {
            model.addAttribute("servicio", servicioOptional.get());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/registrarServicios"; // Reutiliza la plantilla para edición
        } else {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Servicio no encontrado para editar.");
            return "redirect:/listaServicios";
        }
    }

    // --- Procesar el formulario de ACTUALIZACIÓN de servicio ---
    // ESTE ES EL NUEVO @PostMapping que necesitas para manejar las actualizaciones.
    @PostMapping("/editarServicio/{id}") // Este endpoint debe coincidir con th:action cuando el ID NO es nulo
    public String actualizarServicio(@PathVariable("id") Long id,
                                     @Valid @ModelAttribute("servicio") Servicio servicio,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        if (result.hasErrors()) {
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/registrarServicios";
        }
        try {

            servicio.setIdServicio(id);
            servicioServicio.guardarServicio(servicio); // Este método debe manejar la actualización
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Servicio actualizado exitosamente!");
            return "redirect:/listaServicios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al actualizar el servicio: " + e.getMessage());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/registrarServicios";
        }
    }

    @PostMapping("/eliminarServicio/{id}")
    public String eliminarServicio(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            servicioServicio.eliminarServicio(id);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Servicio eliminado exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al eliminar el servicio: " + e.getMessage());
        }
        return "redirect:/listaServicios";
    }
}