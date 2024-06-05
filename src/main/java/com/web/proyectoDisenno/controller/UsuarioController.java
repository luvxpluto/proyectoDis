package com.web.proyectoDisenno.controller;

import com.web.proyectoDisenno.model.Usuario;
import com.web.proyectoDisenno.service.UsuarioService;
import com.web.proyectoDisenno.thirdparty.FaceRecognitionService;
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
  private final FaceRecognitionService faceRecognitionService;

  @Autowired
  public UsuarioController(UsuarioService usuarioService){
    this.usuarioService = usuarioService;
    this.faceRecognitionService = new FaceRecognitionService(usuarioService);
  }

  @GetMapping("/")
  public String mostrarLogin() {
    return "faceLogin";
  }

  @PostMapping("/")
  public String procesarLogin(@RequestParam("image") String image, HttpSession session, Model model) {
    Usuario usuario = faceRecognitionService.authenticateUserFaceId(image);

    if (usuario != null) {
      session.setAttribute("usuario", usuario);
      return "redirect:/inicio";
    } else {
      model.addAttribute("error", "Usuario no encontrado");
      return "faceLogin";
    }
  }

  @GetMapping("/logout")
  public String procesarLogout(HttpSession session) {
    session.invalidate();
    return "faceLogin";
  }

  @GetMapping("/usuarios")
  public String listarUsuarios(Model model) {
    List<Usuario> usuarios = usuarioService.getAllUsuarios();
    model.addAttribute("usuarios", usuarios);
    return "usuario-listar";
  }
}
