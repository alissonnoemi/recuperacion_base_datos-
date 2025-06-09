package com.itsqmet.controller;

import com.itsqmet.entity.Demo;
import com.itsqmet.service.DemoServicio;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DemoController {
    private DemoServicio demoServicio;
    public DemoController(DemoServicio demoServicio) {
        this.demoServicio = demoServicio;
    }
    @PostMapping ("/guardarDemo")
    public String guardarDemo(@Valid @ModelAttribute ("demo") Demo demo,
                              BindingResult bildingResult, Model model) {
        if (bildingResult.hasErrors()) {
            model.addAttribute("errors", bildingResult.getAllErrors());
            return "pages/demo";
        }else {
            return "redirect:/agradecimiento";
        }
    }
}
