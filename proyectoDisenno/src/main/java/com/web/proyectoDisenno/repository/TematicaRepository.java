package com.web.proyectoDisenno.repository;

import com.web.proyectoDisenno.model.Tematica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TematicaRepository extends JpaRepository<Tematica, Long> {
  Tematica findByNombreAndUsuarioIdentificacion(String nombre, String identificacion);
  List<Tematica> findByUsuarioIdentificacionOrderByNombreAsc(String identificacion);
}
