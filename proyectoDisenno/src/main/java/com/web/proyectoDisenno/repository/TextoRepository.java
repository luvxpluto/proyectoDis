package com.web.proyectoDisenno.repository;

import com.web.proyectoDisenno.model.Texto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextoRepository extends JpaRepository<Texto, Long> {
  List<Texto> findByTematicaNombre(String nombreTematica);
}
