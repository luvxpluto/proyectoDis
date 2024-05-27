package com.web.proyectoDisenno.service;

import com.web.proyectoDisenno.model.Texto;
import com.web.proyectoDisenno.repository.TextoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextoService {
  private final TextoRepository textoRepository;

  @Autowired
  public TextoService(TextoRepository textoRepository) {
    this.textoRepository = textoRepository;
  }

  public List<Texto> getTextosByTematica(String nombreTematica) {
    return textoRepository.findByTematicaNombre(nombreTematica);
  }

  public void saveTexto(Texto texto) {
    textoRepository.save(texto);
  }

  public Texto getTextoById(Long id) {
    return textoRepository.findById(id).orElse(null);
  }
}
