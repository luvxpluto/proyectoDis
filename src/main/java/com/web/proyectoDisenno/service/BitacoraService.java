package com.web.proyectoDisenno.service;

import com.web.proyectoDisenno.model.Bitacora;
import com.web.proyectoDisenno.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BitacoraService {
  private final BitacoraRepository bitacoraRepository;

  @Autowired
  public BitacoraService(BitacoraRepository bitacoraRepository) {
    this.bitacoraRepository = bitacoraRepository;
  }

  public void saveBitacora(Bitacora bitacora) {
    bitacoraRepository.save(bitacora);
  }
}
