package com.web.proyectoDisenno.controller;

import com.web.proyectoDisenno.model.Usuario;
import com.web.proyectoDisenno.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UsuarioController {
  private final UsuarioService usuarioService;

  @Autowired
  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @GetMapping("/")
  public String mostrarLogin() {
    return "login";
  }

  @PostMapping("/")
  public String procesarLogin(@RequestParam String identificacion, HttpSession session, Model model) {
    Usuario usuario = usuarioService.getUsuarioByIdentificacion(identificacion);
    //Cambiar esto por autenticación con reconocimiento facial
    //Ver como pasar la imagen a base64
    //Ver como llamar este controller desde el front en js
    //Se ocupa una instancia de FaceRecognitionService para autenticar al usuario
    if (usuario != null) {
      session.setAttribute("usuario", usuario);
      return "redirect:/inicio";
    } else {
      model.addAttribute("error", "Usuario no encontrado");
      return "login";
    }
  }

  @GetMapping("/logout")
  public String procesarLogout(HttpSession session) {
    session.invalidate();
    return "login";
  }

  @GetMapping("/usuarios")
  public String listarUsuarios(Model model) {
    List<Usuario> usuarios = usuarioService.getAllUsuarios();
    model.addAttribute("usuarios", usuarios);
    return "usuario-listar";
  }
}
