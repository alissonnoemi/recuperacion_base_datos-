package com.itsqmet.controller;

import com.itsqmet.entity.Contacto;
import com.itsqmet.entity.Negocio;
import com.itsqmet.service.ContactoServicio;
import com.itsqmet.service.NegocioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class NegocioController {
    @Autowired
    private NegocioServicio negocioServicio;

    //leer
    @GetMapping("/negocios")
    public String listarNegocios(@RequestParam(name = "buscarNombre", required = false, defaultValue = "")
                                 String buscarNegocio, Model model) {
        List<Negocio>negocios= negocioServicio.buscarNegocioPorNombre(buscarNegocio);
        model.addAttribute("negocios", negocios);
        model.addAttribute("buscarNombre", buscarNegocio);
        return "pages/listaNegocios";
    }
    //insertar
    @GetMapping ("/pages/gratis")
    public String negocios(Model model){
        model.addAttribute("negocios", new Negocio());
        return "pages/gratis";
    }
    @PostMapping ("/guardarNegocio")
    public String crearNegocio(Negocio negocio){
        negocioServicio.guardarNegocio(negocio);
        return "redirect:/negocios";
    }
    //actualizar
    @GetMapping("/editarNegocio/{id}")
    public String actualizarNegocio(@PathVariable Long id, Model model){
        Optional<Negocio> negocio = negocioServicio.buscarNegocioPorId(id);
        model.addAttribute("negocio", negocio);
        return "templates/gratis";
    }
    //eliminar
    @GetMapping("/eliminarNegocio/{id}")
    public String eliminarNegocio(@PathVariable Long id, Model model){
        negocioServicio.eliminarNegocio(id);
        return "redirect:/negocios";
    }

}