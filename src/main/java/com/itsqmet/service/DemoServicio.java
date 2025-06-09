package com.itsqmet.service;

import com.itsqmet.entity.Demo;
import com.itsqmet.repository.demoRepositorio;
import org.springframework.stereotype.Service;

@Service
public class DemoServicio {
    private demoRepositorio demoRepositorio;
    public DemoServicio(demoRepositorio demoRepositorio) {
        this.demoRepositorio = demoRepositorio;
    }
    public void guardarDemo(Demo demo) {
        demoRepositorio.save(demo);
    }
}
