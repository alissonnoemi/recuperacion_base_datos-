package com.itsqmet.controller;

import com.itsqmet.entity.LogNegocio;
import com.itsqmet.entity.Negocio;
import com.itsqmet.service.NegocioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class NegocioController {
    @Autowired
    private NegocioServicio negocioServicio;

    // Listar negocios
    @GetMapping("/negocios")
    public String listarNegocios(@RequestParam(name = "buscarNombre", required = false, defaultValue = "") String buscarNegocio, Model model) {
        List<Negocio> negocios = negocioServicio.buscarNegocioPorNombre(buscarNegocio);
        model.addAttribute("negocios", negocios);
        model.addAttribute("buscarNombre", buscarNegocio);
        return "pages/listaNegocio";
    }

    // Formulario de registro de negocio
    @GetMapping("/gratis")
    public String negocios(Model model) {
        model.addAttribute("negocio", new Negocio());
        return "pages/gratis";
    }

    // Guardar negocio
    @PostMapping("/guardarNegocio")
    public String crearNegocio(@Valid  @ModelAttribute("negocio") Negocio negocio,
    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("negocio", negocio);
            return "pages/gratis";
        } else {
            return "redirect:/agradecimiento";
        }
    }

    // Editar negocio
    @GetMapping("/editarNegocio/{id}")
    public String actualizarNegocio(@PathVariable Long id, Model model) {
        Optional<Negocio> negocio = negocioServicio.buscarNegocioPorId(id);
        model.addAttribute("negocio", negocio.orElse(new Negocio()));
        return "pages/gratis";
    }

    // Eliminar negocio
    @GetMapping("/eliminarNegocio/{id}")
    public String eliminarNegocio(@PathVariable Long id) {
        negocioServicio.eliminarNegocio(id);
        return "redirect:/negocios";
    }
    @GetMapping ("/profesionales")
    public String mostrarProfesionales() {
        return "/pages/profesionales";
    }


}