package com.web.proyectoDisenno.repository;


import com.web.proyectoDisenno.model.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {

  List<Bitacora> findByTipo(String tipo);
  List<Bitacora> findByTipoAndUsuarioIdentificacion(String tipo, String identificacion);

  @Query("SELECT b FROM Bitacora b WHERE b.fecha = :hoy AND b.tipo = :tipo AND b.usuario.identificacion = :identificacion")
  List<Bitacora> findByFechaAndTipo(@Param("hoy") LocalDate hoy, @Param("tipo") String tipo, @Param("identificacion") String identificacion);

@Query("SELECT b FROM Bitacora b WHERE b.fecha = :hoy AND b.hora BETWEEN :horaInicio AND :horaFin AND b.tipo = :tipo AND b.usuario.identificacion = :identificacion")
List<Bitacora> findBitacorasEntreHorasPorTipo(@Param("hoy") LocalDate hoy, @Param("horaInicio") LocalTime horaInicio, @Param("horaFin") LocalTime horaFin,
                                              @Param("tipo") String tipo, @Param("identificacion") String identificacion);
}