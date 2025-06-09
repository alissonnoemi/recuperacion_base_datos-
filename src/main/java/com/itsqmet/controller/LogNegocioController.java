// src/main/java/com/itsqmet/controller/LogNegocioController.java
package com.itsqmet.controller;

import com.itsqmet.entity.Cliente;
import com.itsqmet.entity.LogNegocio;
import com.itsqmet.service.LogServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogNegocioController {
    @Autowired
    private LogServicio logServicio;

    @GetMapping("/inicioProfesionales")
    public String mostrarLoginn(Model model) {
        model.addAttribute("logNegocio", new LogNegocio());
        return "pages/inicioProfesionales";
    }

  @PostMapping ("/loginNegocio")
    public String procesarLogin(@ModelAttribute LogNegocio logNegocio, Model model) {
        boolean autenticado = logServicio.validarCredenciales(logNegocio.getEmail(), logNegocio.getPassword());
        if (autenticado) {
            return "redirect:/profesionales";
        } else {
            model.addAttribute("mensajeError", "Usuario o contraseña incorrectos");
            return "pages/inicioProfesionales";
        }
    }

    @GetMapping("/registroNegocio")
    public String mostrarRegistroNegocio(Model model) {
        model.addAttribute("logNegocio", new LogNegocio());
        return "pages/registroNegocio";
    }

    @PostMapping("/registroNegocio")
    public String registrarCliente(@ModelAttribute LogNegocio logNegocio, Model model) {
        logServicio.registrarProfesional(logNegocio);
        return "redirect:/inicioProfesionales";
    }
}