package com.itsqmet.controller;

import com.itsqmet.entity.Citas;
import com.itsqmet.entity.Cliente;
import com.itsqmet.entity.Profesional;
import com.itsqmet.entity.Servicio;
import com.itsqmet.service.ClienteServicio;
import com.itsqmet.service.ProfesionalServicio;
import com.itsqmet.service.ServicioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ClienteController {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private ServicioServicio servicioServicio;
    // Listar todos los pacientes
    @GetMapping("/lista")
    public String listaClientes(Model model) {
        model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
        return "pages/listaClientes";
    }
    // Mostrar formulario para crear un nuevo paciente
    @GetMapping("/registroCliente")
    public String mostrarFormularioNuevoPaciente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "pages/registroCliente";
    }

    // Procesa el registro
    @PostMapping("/registroCliente")
    public String guardarPaciente(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "pages/inicioClientes";
        }
        try {
            clienteServicio.guardarPaciente(cliente);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Paciente guardado exitosamente!");
            return "redirect:/lista";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", e.getMessage());
            return "pages/crearPaciente";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al guardar el paciente: " + e.getMessage());
            return "pages/crearPaciente";
        }
    }

    // Mostrar formulario para editar un paciente
    @GetMapping("/editarCliente/{id}")
    public String mostrarFormularioEditarPaciente(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Cliente> pacienteOptional = clienteServicio.obtenerClientePorId(id);
        if (pacienteOptional.isPresent()) {
            model.addAttribute("cliente", pacienteOptional.get());
            return "pages/registroCliente";
        } else {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Paciente no encontrado para editar.");
            return "redirect:/lista";
        }
    }

    // Procesar la edición de un paciente
    @PostMapping("/actualizarCliente/{id}")
    public String actualizarCliente(@PathVariable("id") Long id, @Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "pages/registroCliente";
        }
        try {
            clienteServicio.actualizarPaciente(id, cliente);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Paciente actualizado exitosamente!");
            return "redirect:/lista";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", e.getMessage());
            return "pages/editarPaciente";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al actualizar el paciente: " + e.getMessage());
            return "pages/editarPaciente";
        }
    }

    // Eliminar un paciente
    @PostMapping("/eliminarCliente/{id}")
    public String eliminarPaciente(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteServicio.eliminarPaciente(id);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Paciente eliminado exitosamente!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al eliminar el paciente.");
        }
        return "redirect:/lista";
    }
    @GetMapping("/inicioClientes")
    public String mostrarLogin(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "pages/inicioClientes";
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Cliente cliente, Model model) {
        boolean autenticado = clienteServicio.validarCredenciales(cliente.getEmail(), cliente.getPassword());
        if (autenticado) {
            Citas nuevaCita = new Citas();
            nuevaCita.setCliente(new Cliente());
            nuevaCita.setServicio(new Servicio());
            nuevaCita.setProfesional(new Profesional());

            model.addAttribute("cita", nuevaCita);
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
            model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
            return "pages/cita";
        } else {
            model.addAttribute("mensajeError", "Usuario o contraseña incorrectos");
            return "pages/inicioClientes";
        }
    }

}