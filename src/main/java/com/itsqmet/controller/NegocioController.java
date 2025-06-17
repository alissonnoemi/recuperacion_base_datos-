package com.itsqmet.controller;

import com.itsqmet.dto.NegocioRegistroDTO;
import com.itsqmet.entity.Negocio;
import com.itsqmet.service.CitasServicio;
import com.itsqmet.service.NegocioServicio;
import com.itsqmet.service.ProfesionalServicio;
import com.itsqmet.service.ServicioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
public class NegocioController {
    @Autowired
    private NegocioServicio negocioServicio;
    @Autowired
    private CitasServicio citasServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private ServicioServicio servicioServicio;

    // --- Listar todos los negocios con Paginación y Búsqueda (sin cambios) ---
    @GetMapping("/negocios")
    public String listarNegocios(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idNegocio") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search
    ) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Negocio> negocioPage;
        if (search != null && !search.trim().isEmpty()) {
            negocioPage = negocioServicio.buscarNegocios(search, pageable);
        } else {
            negocioPage = negocioServicio.obtenerTodosLosNegocios(pageable);
        }

        model.addAttribute("negocios", negocioPage.getContent());
        model.addAttribute("currentPage", negocioPage.getNumber());
        model.addAttribute("totalPages", negocioPage.getTotalPages());
        model.addAttribute("totalItems", negocioPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("search", search);

        return "pages/listaNegocio";
    }

    // --- Mostrar formulario para crear un nuevo negocio (con DTO) ---
    // Ahora, al cargar el formulario, no pre-seleccionamos ningún plan de pago,
    // el usuario lo elegirá.
    @GetMapping("/gratis")
    public String mostrarFormularioNuevoNegocio(Model model) {
        NegocioRegistroDTO negocioDto = new NegocioRegistroDTO();
        // No pre-establecemos un plan aquí, el usuario lo seleccionará en el frontend
        // y se enviará en el POST.
        model.addAttribute("negocioDto", negocioDto);
        return "pages/gratis";
    }

    // --- Procesar el formulario de creación de negocio (con DTO) ---
    @PostMapping("/guardarNegocio")
    public String guardarNegocio(@Valid @ModelAttribute("negocioDto") NegocioRegistroDTO negocioDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        // Aquí, las validaciones de @NotBlank y @Pattern en el DTO se encargarán
        // de los campos de Negocio y de Pago.
        // No necesitamos validación condicional aquí, ya que SIEMPRE se requieren los datos de pago.

        if (result.hasErrors()) {
            // Si hay errores de validación, vuelve al formulario
            // El DTO (negocioDto) ya contiene los datos ingresados por el usuario.
            // Los mensajes de error se mostrarán automáticamente por Thymeleaf.
            model.addAttribute("negocioDto", negocioDto); // Aseguramos que el DTO con errores se pase de vuelta
            return "pages/gratis";
        }

        try {
            // Convertir DTO a Entidad Negocio para guardar
            Negocio negocio = new Negocio();
            // Si es una edición, el ID estará presente
            if (negocioDto.getIdNegocio() != null) {
                negocio.setIdNegocio(negocioDto.getIdNegocio());
            }
            negocio.setNombreCompleto(negocioDto.getNombreCompleto());
            negocio.setEmailProfesional(negocioDto.getEmailProfesional());
            negocio.setNombreNegocio(negocioDto.getNombreNegocio());
            negocio.setTipoNegocio(negocioDto.getTipoNegocio());
            negocio.setDireccion(negocioDto.getDireccion());
            negocio.setTelefono(negocioDto.getTelefono());
            negocio.setPlan(negocioDto.getPlan()); // El plan elegido por el usuario

            // --- Lógica de procesamiento de datos de pago (SIN COBRO INMEDIATO) ---
            // En este punto, solo guardarías o tokenizarías la información de la tarjeta.
            // La facturación real se gestionaría con un sistema de suscripciones externo
            // que se activaría después de los 14 días.
            // Para fines de ejemplo, aquí podrías registrar que el usuario se registró
            // con un plan y los datos de pago.
            System.out.println("Usuario " + negocioDto.getEmailProfesional() + " se registró con el plan: " + negocioDto.getPlan());
            System.out.println("Datos de pago recibidos (NO procesados para cobro inmediato):");
            System.out.println("  Tarjeta: " + negocioDto.getCardNumber());
            System.out.println("  Fecha: " + negocioDto.getExpiryDate());
            System.out.println("  CVV: " + negocioDto.getCvv());
            System.out.println("  Dirección Fact.: " + negocioDto.getBillingAddress());

            // Aquí NO se realiza un cobro. Solo se registran los datos para la futura facturación.
            // Podrías tener un campo en la entidad Negocio o una entidad separada para almacenar
            // el ID de suscripción de la pasarela de pago, o simplemente marcar la fecha de inicio
            // de la prueba para activar la facturación en 14 días.

            negocioServicio.guardarNegocio(negocio); // Guarda la entidad Negocio con el plan elegido
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "¡Excelente! Has elegido el Plan " + negocio.getPlan().toUpperCase() + ". Disfruta de tus 14 días de prueba gratuita. La facturación comenzará después.");
            return "redirect:/negocios"; // O a una página de confirmación

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al registrar el negocio: " + e.getMessage());
            model.addAttribute("negocioDto", negocioDto); // Mantiene los datos y errores en el formulario
            return "pages/gratis";
        }
    }

    // --- Mostrar formulario para editar un negocio (se sigue cargando un Negocio, pero se mapea al DTO para el form) ---
    @GetMapping("/editarNegocio/{id}")
    public String mostrarFormularioEditarNegocio(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Negocio> negocioOptional = negocioServicio.obtenerNegocioPorId(id);
        if (negocioOptional.isPresent()) {
            Negocio negocio = negocioOptional.get();
            // Convertir Entidad Negocio a DTO para precargar el formulario
            NegocioRegistroDTO negocioDto = new NegocioRegistroDTO();
            negocioDto.setIdNegocio(negocio.getIdNegocio());
            negocioDto.setNombreCompleto(negocio.getNombreCompleto());
            negocioDto.setEmailProfesional(negocio.getEmailProfesional());
            negocioDto.setNombreNegocio(negocio.getNombreNegocio());
            negocioDto.setTipoNegocio(negocio.getTipoNegocio());
            negocioDto.setDireccion(negocio.getDireccion());
            negocioDto.setTelefono(negocio.getTelefono());
            negocioDto.setPlan(negocio.getPlan()); // El plan actual del negocio

            // En este flujo, si se edita, los datos de pago no se precargan por seguridad
            // ni porque se esperaría una edición del plan en este mismo formulario.
            // Si la edición implica cambio de plan, es un flujo separado.

            model.addAttribute("negocioDto", negocioDto); // Usamos "negocioDto"
            return "pages/gratis"; // Reutilizamos el formulario "gratis" para editar
        } else {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Negocio no encontrado.");
            return "redirect:/negocios";
        }
    }

    // Eliminar Negocio y Ver Historial CitasNegocio (sin cambios)
    @PostMapping("/eliminarNegocio/{id}")
    public String eliminarNegocio(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            negocioServicio.eliminarNegocio(id);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Negocio eliminado exitosamente!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al eliminar el negocio.");
        }
        return "redirect:/negocios";
    }

    @GetMapping("/historialCitasNegocio/{idNegocio}")
    public String verHistorialCitasNegocio(@PathVariable("idNegocio") Long idNegocio, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Negocio> negocioOptional = negocioServicio.obtenerNegocioPorId(idNegocio);
            if (negocioOptional.isPresent()) {
                Negocio negocio = negocioOptional.get();
                model.addAttribute("negocio", negocio);
                model.addAttribute("citas", citasServicio.obtenerCitasPorNegocio(idNegocio));
                model.addAttribute("profesionales", profesionalServicio.obtenerProfesionalesPorNegocio(idNegocio));
                model.addAttribute("servicios", servicioServicio.obtenerServiciosPorNegocio(idNegocio));
                return "/pages/historialCitasNegocio";
            } else {
                redirectAttributes.addFlashAttribute("mensajeTipo", "error");
                redirectAttributes.addFlashAttribute("mensajeCuerpo", "Negocio no encontrado.");
                return "redirect:/negocios";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al obtener el historial de citas: " + e.getMessage());
            return "redirect:/negocios";
        }
    }
}