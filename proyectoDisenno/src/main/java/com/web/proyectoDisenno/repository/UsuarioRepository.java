package com.web.proyectoDisenno.repository;

import com.web.proyectoDisenno.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
  Usuario findByIdentificacion(String identificacion);
}
