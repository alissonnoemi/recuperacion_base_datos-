package com.itsqmet.repository;

import com.itsqmet.entity.Citas;
import com.itsqmet.entity.Cliente;
import com.itsqmet.entity.Negocio;
import com.itsqmet.entity.Profesional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface citasRepositorio extends MongoRepository<Citas, Long> {
    List<Citas> findByCliente(Cliente cliente);
    List<Citas> findByProfesionalOrderByFechaHoraInicioAsc(Profesional profesional);

    @Query("SELECT c FROM Citas c WHERE LOWER(c.profesional.nombreCompleto) LIKE LOWER(CONCAT('%', :nombreProfesional, '%')) ORDER BY c.fechaHoraInicio ASC")
    List<Citas> findByProfesionalNombreCompletoContainingIgnoreCase(@Param("nombreProfesional") String nombreProfesional);

    List<Citas> findByProfesional_NegocioOrderByFechaHoraInicioAsc(Negocio negocio);

    // *** Método para encontrar citas que se superpongan (para nuevas citas) ***
    @Query("SELECT c FROM Citas c WHERE c.profesional = :profesional " +
            "AND c.fechaHoraInicio <= :finCitaNueva AND c.fechaHoraFin >= :inicioCitaNueva")
    List<Citas> findConflictingAppointments(@Param("profesional") Profesional profesional,
                                            @Param("inicioCitaNueva") LocalDateTime inicioCitaNueva,
                                            @Param("finCitaNueva") LocalDateTime finCitaNueva);

    // *** Método para encontrar citas que se superpongan (excluyendo la propia, para actualizaciones) ***
    @Query("SELECT c FROM Citas c WHERE c.profesional = :profesional " +
            "AND c.idCita != :idCitaActual " +
            "AND c.fechaHoraInicio <= :finCitaNueva AND c.fechaHoraFin >= :inicioCitaNueva")
    List<Citas> findConflictingAppointmentsExcludingSelf(@Param("profesional") Profesional profesional,
                                                         @Param("inicioCitaNueva") LocalDateTime inicioCitaNueva,
                                                         @Param("finCitaNueva") LocalDateTime finCitaNueva,
                                                         @Param("idCitaActual") Long idCitaActual);
}
