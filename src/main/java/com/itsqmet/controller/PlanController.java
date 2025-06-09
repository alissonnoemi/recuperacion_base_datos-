package com.itsqmet.controller;

import com.itsqmet.entity.Plan;
import com.itsqmet.service.PlanServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/planes")
public class PlanController {
    private final PlanServicio planServicio;
    public PlanController(PlanServicio planServicio) {
        this.planServicio = planServicio;
    }

}