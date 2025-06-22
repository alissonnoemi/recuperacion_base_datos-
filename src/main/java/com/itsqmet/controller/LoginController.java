package com.itsqmet.controller;

import com.itsqmet.entity.*;
import com.itsqmet.service.ClienteServicio;
import com.itsqmet.service.NegocioServicio;
import com.itsqmet.service.ProfesionalServicio;
import com.itsqmet.service.ServicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private ServicioServicio servicioServicio;
    @Autowired
    private NegocioServicio negocioServicio;
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Cliente cliente, Model model) {
        boolean autenticado = clienteServicio.validarCredenciales(cliente.getEmail(), cliente.getPassword());
        if (autenticado) {
            Cliente clienteAutenticado = clienteServicio.obtenerClientePorEmail(cliente.getEmail()).orElse(null);
            if (clienteAutenticado != null) {

                Citas nuevaCita = new Citas();
                nuevaCita.setCliente(clienteAutenticado);

                nuevaCita.setServicio(new Servicio());
                nuevaCita.setProfesional(new Profesional());

                model.addAttribute("cita", nuevaCita);
                model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
                model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
                model.addAttribute("servicios", servicioServicio.obtenerTodosLosServicios());
                return "pages/cita";
            } else {
                model.addAttribute("mensajeError", "Error al obtener datos del cliente autenticado.");
                return "pages/inicioClientes";
            }
        } else {
            model.addAttribute("mensajeError", "Correo electrónico o contraseña incorrectos.");
            return "pages/inicioClientes";
        }
    }
    @PostMapping("/loginNegocio")
    public String loginNegocio(Negocio negocio, Model model) {
        Optional<Negocio> registrado = negocioServicio.findByEmail(negocio.getEmailProfesional());
        if (registrado.isPresent() && registrado.get().getPassword().equals(negocio.getPassword())) {
            return "redirect:/agradecimiento";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "pages/inicioProfesionales";
        }
    }
}
