package com.itsqmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    @GetMapping ("/")
    public String mostrarHome() {
        return "index";
    }
    @GetMapping ("/gratis")
    public String mostrarGratis() {
        return "gratis";
    }
    @GetMapping ("/salonesBelleza")
    public String mostrarSalonesBelleza() {
        return "salonesBelleza";
    }
    @GetMapping ("/clinicas")
    public String mostrarClinicas() {
        return "clinicas";
    }
    @GetMapping ("/consultorias")
    public String mostrarConsultorias() {
        return "consultorias";
    }
    @GetMapping ("/educacion")
    public String mostrarEducacion() {
        return "educacion";
    }
    @GetMapping ("/talleres")
    public String mostrarTalleres() {
        return "talleres";
    }
    @GetMapping ("/demo")
    public String mostrarDemo() {
        return "demo";
    }
    @GetMapping ("/gestionPersonal")
    public String mostrarGestionPersonal() {
        return "gestionPersonal";
    }
    @GetMapping ("/agendamiento")
    public String mostrarAgendamiento() {
        return "agendamiento";
    }
    @GetMapping ("/control")
    public String mostrarControl() {
        return "control";
    }
    @GetMapping ("/precios")
    public String mostrarPrecios() {
        return "precios";
    }
    @GetMapping ("/agradecimiento")
    public String mostrarAgradecimiento() {
        return "agradecimiento";
    }
}
