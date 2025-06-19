package com.itsqmet.controller;

import com.itsqmet.entity.Contacto;
import com.itsqmet.repository.contactoRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactoController {
    private contactoRepositorio contactoRepositorio;
    public ContactoController(contactoRepositorio contactoRepositorio) {
        this.contactoRepositorio = contactoRepositorio;
    }
    @PostMapping ("/enviarContacto")
    public String enviarContacto(@ModelAttribute  Contacto contacto) {
        contactoRepositorio.save(contacto);
        return "redirect:/agradecimiento";
    }
}
