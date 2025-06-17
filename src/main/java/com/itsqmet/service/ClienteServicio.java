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
    public Cliente guardarCliente(Cliente cliente) {
        // Validación para el nombre (mantener).
        if (cliente.getNombreCompleto() == null || cliente.getNombreCompleto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        // Puedes añadir más validaciones específicas de negocio aquí si son necesarias
        // antes de guardar/actualizar.

        // JpaRepository.save(cliente) se encarga de:
        // - INSERTAR si cliente.getId() es null.
        // - ACTUALIZAR si cliente.getId() NO es null.
        return clienteRepositorio.save(cliente);
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepositorio.findById(id);
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepositorio.findAll();
    }

    // --- ¡ELIMINA ESTE MÉTODO COMPLETO DE TU SERVICIO! ---
    // Ya no es necesario porque 'guardarCliente' lo cubre.
    /*
    @Transactional
    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        Cliente clienteExistente = clienteRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));

        clienteExistente.setNombreCompleto(clienteActualizado.getNombreCompleto());
        clienteExistente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
        clienteExistente.setGenero(clienteActualizado.getGenero());
        clienteExistente.setTelefono(clienteActualizado.getTelefono());
        clienteExistente.setEmail(clienteActualizado.getEmail());
        // Aquí no se actualiza la contraseña, lo cual es correcto si no la quieres siempre requerida al editar.

        return clienteRepositorio.save(clienteExistente);
    }
    */

    public void eliminarCliente(Long id) {
        if (!clienteRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + id);
        }
        clienteRepositorio.deleteById(id);
    }

    public boolean validarCredenciales(String email, String password) {
        List<Cliente> clientes = clienteRepositorio.findByEmailContainingIgnoreCase(email);
        if (clientes.isEmpty()) return false;

        Cliente cliente = clientes.get(0);
        return cliente.getPassword().equals(password);
    }

    public Optional<Cliente> obtenerClientePorEmail(String email) {
        List<Cliente> clientes = clienteRepositorio.findByEmailContainingIgnoreCase(email);
        if (clientes.isEmpty()) return Optional.empty();

        return Optional.of(clientes.get(0));
    }
}