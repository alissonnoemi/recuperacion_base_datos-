package com.itsqmet.controller;

import com.itsqmet.entity.Contacto;
import com.itsqmet.repository.contactoRepositorio;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactoController {
    private contactoRepositorio contactoRepositorio;
    public ContactoController(contactoRepositorio contactoRepositorio) {
        this.contactoRepositorio = contactoRepositorio;
    }
    @PostMapping ("/enviarContacto")
    public String enviarContacto(@Valid  @ModelAttribute ("contacto")  Contacto contacto,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "pages/contacto";
        }else {
            return "redirect:/agradecimiento";
        }
    }
    @GetMapping("/contacto")
    public String mostrarFormularioContacto(Model model) {
        model.addAttribute("contacto", new Contacto());
        return "pages/contacto";
    }
}
