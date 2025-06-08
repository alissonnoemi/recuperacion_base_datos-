package com.itsqmet.service;

import com.itsqmet.entity.Negocio;
import com.itsqmet.repository.negocioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NegocioServicio {
@Autowired
    private negocioRepositorio negocioRepositorio;
    public  List <Negocio> mostrarNegocios() {
        return negocioRepositorio.findAll();
    }
    public List <Negocio> buscarNegocioPorNombre(String buscarNegocio) {
        if (buscarNegocio == null || buscarNegocio.isEmpty()) {
            return negocioRepositorio.findAll();
        } else {
            return negocioRepositorio.findBytipoNegocioContainingIgnoreCase(buscarNegocio);
        }
    }
    public Optional<Negocio> buscarNegocioPorId (Long id) {
        return negocioRepositorio.findById(id);
    }
    public Negocio guardarNegocio(Negocio negocio) {
        return negocioRepositorio.save(negocio);
    }
    public void eliminarNegocio(Long id) {
        negocioRepositorio.deleteById(id);
    }


}
