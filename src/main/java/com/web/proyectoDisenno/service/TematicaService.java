package com.web.proyectoDisenno.service;

import com.web.proyectoDisenno.model.Tematica;
import com.web.proyectoDisenno.repository.TematicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TematicaService {
  private final TematicaRepository tematicaRepository;

  @Autowired
  public TematicaService(TematicaRepository tematicaRepository) {
    this.tematicaRepository = tematicaRepository;
  }

  public void saveTematica(Tematica tematica) {
    tematicaRepository.save(tematica);
  }

  public Tematica getTematicaByNombreAndUsuarioIdentificacion(String nombre, String identificacion) {
    return tematicaRepository.findByNombreAndUsuarioIdentificacion(nombre, identificacion);
  }

  public List<Tematica> getTematicasByUsuarioIdentificacion(String identificacion) {
    return tematicaRepository.findByUsuarioIdentificacionOrderByNombreAsc(identificacion);
  }
}
