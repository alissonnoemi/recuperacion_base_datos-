package com.itsqmet.controller;

import com.itsqmet.entity.Citas;
import com.itsqmet.service.CitasServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CitasController {
    @Autowired
    private CitasServicio citasServicio;

    //leer
    @GetMapping("/citas")
    public String listarCitas(@RequestParam(name = "buscarCita", required = false, defaultValue = "")
                                 String buscarCita, Model model) {
        List<Citas> citas= citasServicio.buscarCitaPorEspecialista(buscarCita);
        model.addAttribute("citas", citas);
        model.addAttribute("buscarNombre", buscarCita);
        return "pages/listaCita";
    }
    @GetMapping("/listaCita")
    public String mostrarListaCita(Model model) {
        model.addAttribute("citas", citasServicio.listarCitas());
        return "/pages/listaCita";
    }

    //insertar
    @GetMapping ("/cita")
    public String cita(Model model){
        model.addAttribute("cita", new Citas());
        return "pages/cita";
    }
    @PostMapping("/guardarCita")
    public String crearCita(Citas cita){
        citasServicio.guardarCita(cita);
        return "redirect:/citas";
    }
    //actualizar
    @GetMapping("/editarCita/{id}")
    public String actualizarCita(@PathVariable Long id, Model model){
        Optional<Citas> cita = citasServicio.buscarCitaPorId(id);
        model.addAttribute("cita", cita);
        return "pages/cita";
    }
    //eliminar
    @GetMapping("/eliminarCita/{id}")
    public String eliminarCita(@PathVariable Long id, Model model){
        citasServicio.eliminarCita(id);
        return "redirect:/citas";
    }

}
