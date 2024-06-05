package com.web.proyectoDisenno.service;

import com.web.proyectoDisenno.model.Bitacora;
import com.web.proyectoDisenno.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Bitacora> getBitacorasByUsuarioIdentificacionAndTipo(String identificacion, String tipo){
        return bitacoraRepository.findByUsuarioIdentificacionAndTipo(identificacion, tipo);
    }

    public List<Bitacora> getBitacorasByFechaUsuarioIdentificacionAndTipo(LocalDateTime fecha, String identificacion, String tipo){
        return bitacoraRepository.findByFechaUsuarioIdentificacionAndTipo(fecha, identificacion, tipo);
    }

    public List<Bitacora> getBitacorasByTipo(String tipo) {
        return bitacoraRepository.findByTipo(tipo);
    }
}
