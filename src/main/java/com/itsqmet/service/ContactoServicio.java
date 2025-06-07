package com.itsqmet.service;

import com.itsqmet.entity.Contacto;
import com.itsqmet.repository.contactoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoServicio {
@Autowired
private contactoRepositorio contactoRepositorio;
    public List<Contacto> obtenerTodosLosContactos() {
        return contactoRepositorio.findAll();
    }
    //  buscar contactos por email:
    public List<Contacto> buscarContactoPorEmail (String buscarEmail) {
        if (buscarEmail == null || buscarEmail.isEmpty()) {
            return contactoRepositorio.findAll();
        }else {
            return contactoRepositorio.findByEmailContainingIgnoreCase(buscarEmail);
        }
    }
    //  guardar contacto:
    public Contacto guardarContacto(Contacto contacto) {
        return contactoRepositorio.save(contacto);
    }
    //  eliminar contacto:
    public void eliminarContacto(Long id) {
        contactoRepositorio.deleteById(id);
    }
}
