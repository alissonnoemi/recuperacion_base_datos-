package com.itsqmet.service;

import com.itsqmet.entity.Cliente;

import com.itsqmet.repository.clienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {

    @Autowired
    private clienteRepositorio clienteRepositorio;

    public boolean validarCredenciales(String email, String password) {
        Cliente cliente = clienteRepositorio.findByEmail(email);
        return cliente != null && cliente.getPassword().equals(password);
    }
    public void registrarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }
}