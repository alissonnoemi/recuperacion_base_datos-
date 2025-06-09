package com.itsqmet.service;

import com.itsqmet.entity.Cliente;
import com.itsqmet.entity.LogNegocio;
import com.itsqmet.repository.loginNegocioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LogServicio {
    @Autowired
    private loginNegocioRepositorio loginNegocioRepositorio;

    public boolean validarCredenciales(String email, String password) {
        List<LogNegocio> logNegocios = loginNegocioRepositorio.findByEmailContainingIgnoreCase(email);
        if (logNegocios.isEmpty()) return false;
        LogNegocio logNegocio = logNegocios.get(0);
        return logNegocio.getPassword().equals(password);
    }
    public void registrarProfesional(LogNegocio logNegocio) {
        loginNegocioRepositorio.save(logNegocio);
    }
}
