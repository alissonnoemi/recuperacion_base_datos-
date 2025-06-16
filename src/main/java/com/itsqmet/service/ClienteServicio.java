package com.itsqmet.service;

import com.itsqmet.entity.Cliente;

import com.itsqmet.repository.clienteRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicio {

    @Autowired
    private clienteRepositorio clienteRepositorio;
    @Transactional
    public Cliente guardarPaciente(Cliente cliente) {
        // Puedes añadir validaciones de negocio aquí antes de guardar
        if (cliente.getNombreCompleto() == null || cliente.getNombreCompleto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del paciente no puede estar vacío.");
        }
        return clienteRepositorio.save(cliente);
    }
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepositorio.findById(id);
    }
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepositorio.findAll();
    }
    @Transactional
    public Cliente actualizarPaciente(Long id, Cliente clienteActualizado) {
      Cliente clienteExistente = clienteRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con ID: " + id));

       clienteExistente.setNombreCompleto(clienteActualizado.getNombreCompleto());
        clienteExistente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
        clienteExistente.setGenero(clienteActualizado.getGenero());
        clienteExistente.setTelefono(clienteActualizado.getTelefono());
        clienteExistente.setEmail(clienteActualizado.getEmail());

        return clienteRepositorio.save(clienteExistente);
    }
    public void eliminarPaciente(Long id) {
        if (!clienteRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Paciente no encontrado con ID: " + id);
        }
        clienteRepositorio.deleteById(id);
    }
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