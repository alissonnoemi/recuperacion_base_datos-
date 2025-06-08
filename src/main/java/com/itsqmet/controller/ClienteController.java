package com.itsqmet.controller;

import com.itsqmet.entity.Cliente;
import com.itsqmet.service.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClienteController {
    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/inicioClientes")
    public String mostrarLogin(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "pages/inicioClientes";
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Cliente cliente, Model model) {
        boolean autenticado = clienteServicio.validarCredenciales(cliente.getEmail(), cliente.getPassword());
        if (autenticado) {
            return "redirect:/cita";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "pages/inicioClientes";
        }
    }
    @GetMapping("/registroCliente")
    public String mostrarRegistroCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "pages/registroCliente";
    }

    // Procesa el registro
    @PostMapping("/registroCliente")
    public String registrarCliente(@ModelAttribute Cliente cliente, Model model) {
        clienteServicio.registrarCliente(cliente);
        return "redirect:/inicioClientes";
    }
}