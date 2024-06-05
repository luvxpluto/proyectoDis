package com.web.proyectoDisenno.repository;

import com.web.proyectoDisenno.model.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BitacoraRepository extends JpaRepository<Bitacora, Long>{
    List<Bitacora> findByUsuarioIdentificacionAndTipo(String identificacion, String tipo);
    List<Bitacora> findByFechaUsuarioIdentificacionAndTipo(LocalDateTime fecha, String identificacion, String tipo);
    List<Bitacora> findByTipo(String tipo);
}
