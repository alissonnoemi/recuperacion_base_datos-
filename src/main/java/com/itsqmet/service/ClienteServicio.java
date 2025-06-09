package com.itsqmet.service;

import com.itsqmet.entity.Cliente;

import com.itsqmet.repository.clienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServicio {

    @Autowired
    private clienteRepositorio clienteRepositorio;

    public boolean validarCredenciales(String email, String password) {
        List<Cliente> clientes = clienteRepositorio.findByEmailContainingIgnoreCase(email);
        if (clientes.isEmpty()) return false;
        Cliente cliente = clientes.get(0);
        return cliente.getPassword().equals(password);
    }
    public void registrarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }
}