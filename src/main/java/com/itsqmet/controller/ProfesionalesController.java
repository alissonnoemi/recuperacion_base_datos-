package com.itsqmet.controller;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.entity.Citas;
import com.itsqmet.entity.Negocio;
import com.itsqmet.entity.Profesional;
import com.itsqmet.service.CitasServicio;
import com.itsqmet.service.NegocioServicio;
import com.itsqmet.service.ProfesionalServicio;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfesionalesController {
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private NegocioServicio negocioServicio;
    @Autowired
    private CitasServicio citasServicio;

    @GetMapping("/listaProfesionales")
    public String listarProfesionales(Model model) {
        model.addAttribute("profesionales", profesionalServicio.obtenerTodosLosProfesionales());
        return "pages/listaProfesionales";
    }
    @GetMapping("/crearProfesional")
    public String mostrarFormularioCrearProfesional(Model model) {
        Profesional nuevoProfesional = new Profesional();
        if (nuevoProfesional.getNegocio() == null) {
            nuevoProfesional.setNegocio(new Negocio());
        }
        model.addAttribute("profesional", nuevoProfesional);
        model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
        return "pages/profesionales";
    }
    @PostMapping("/crearProfesional")
    public String crearProfesional(@Valid @ModelAttribute("profesional") Profesional profesional, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales";
        }
        try {
            profesionalServicio.guardarProfesional(profesional);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional creado exitosamente!");
            return "redirect:/listaProfesionales";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al crear el profesional: " + e.getMessage());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales";
        }
    }
    @GetMapping("/editarProfesional/{id}")
    public String mostrarFormularioEditarProfesional(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Profesional> profesionalOptional = profesionalServicio.obtenerProfesionalPorId(id);
        if (profesionalOptional.isPresent()) {
            model.addAttribute("profesional", profesionalOptional.get());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales"; // Usa el mismo formulario
        } else {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional no encontrado para editar.");
            return "redirect:/listaProfesionales";
        }
    }
    @PostMapping("/editarProfesional/{id}")
    public String actualizarProfesional(@PathVariable("id") Long id,
                                        @Valid @ModelAttribute("profesional") Profesional profesional,
                                        BindingResult result,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales";
        }
        try {
            profesional.setIdProfesional(id);
            profesionalServicio.guardarProfesional(profesional);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional actualizado exitosamente!");
            return "redirect:/listaProfesionales";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al actualizar el profesional: " + e.getMessage());
            model.addAttribute("negocios", negocioServicio.obtenerTodosLosNegocios());
            return "pages/profesionales";
        }
    }
    @PostMapping("/eliminarProfesional/{id}")
    public String eliminarProfesional(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            profesionalServicio.eliminarProfesional(id);
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional eliminado exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Error al eliminar el profesional: " + e.getMessage());
        }
        return "redirect:/listaProfesionales";
    }
    @GetMapping("/historialCitas/{idProfesional}")
    public String verHistorialCitasProfesional(@PathVariable("idProfesional") Long idProfesional, Model model, RedirectAttributes redirectAttributes) {
        Optional<Profesional> profesionalOptional = profesionalServicio.obtenerProfesionalPorId(idProfesional);
        if (profesionalOptional.isPresent()) {
            Profesional profesional = profesionalOptional.get();
            model.addAttribute("profesional", profesional);
            model.addAttribute("citas", citasServicio.obtenerCitasPorProfesional(idProfesional));
            return "pages/historialProfesional";
        } else {
            redirectAttributes.addFlashAttribute("mensajeTipo", "error");
            redirectAttributes.addFlashAttribute("mensajeCuerpo", "Profesional no encontrado.");
            return "redirect:/listaProfesionales";
        }
    }

    @GetMapping("/profesionalesPdf")
    public void exportarPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=historial_citas_profesionales.pdf");

        List<Profesional> profesionales = profesionalServicio.obtenerTodosLosProfesionales();

        com.itextpdf.text.Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Historial de Citas de Profesionales"));
        document.add(new Paragraph(" "));

        for (Profesional profesional : profesionales) {
            document.add(new Paragraph("Profesional: " + profesional.getNombreCompleto()));
            if (profesional.getCitas() != null && !profesional.getCitas().isEmpty()) {
                for (Citas cita : profesional.getCitas()) {
                    document.add(new Paragraph(
                            "  Fecha: " + cita.getFechaHoraInicio() +
                                    " | Hora: " + cita.getFechaHoraInicio() +
                                    " | Cliente: " + cita.getCliente().getNombreCompleto()
                    ));
                }
            } else {
                document.add(new Paragraph("  Sin citas registradas."));
            }
            document.add(new Paragraph(" "));
        }
        document.close();
    }
}