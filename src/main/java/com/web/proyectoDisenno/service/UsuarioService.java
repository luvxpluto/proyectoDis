package com.web.proyectoDisenno.service;

import com.web.proyectoDisenno.model.Usuario;
import com.web.proyectoDisenno.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;

  @Autowired
  public UsuarioService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  public List<Usuario> getAllUsuarios() {
    return usuarioRepository.findAll();
  }

  public Usuario getUsuarioByIdentificacion(String identificacion) {
    return usuarioRepository.findByIdentificacion(identificacion);
  }

  public List<String> getUsuariosUrlImage() {
    return getAllUsuarios().stream().map(Usuario::getUrlFoto).toList();
  }

  public Usuario getUsuarioByUrlFoto(String urlFoto) {
    return usuarioRepository.findByUrlFoto(urlFoto);
  }
}
