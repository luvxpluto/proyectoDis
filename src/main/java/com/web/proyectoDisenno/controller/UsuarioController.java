package com.web.proyectoDisenno.controller;

import com.web.proyectoDisenno.model.*;
import com.web.proyectoDisenno.service.BitacoraService;
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
  private final BitacoraService bitacoraService;

  @Autowired
  public UsuarioController(UsuarioService usuarioService, BitacoraService bitacoraService) {
    this.usuarioService = usuarioService;
    this.faceRecognitionService = new FaceRecognitionService(usuarioService);
    this.bitacoraService = bitacoraService;
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
  public String listarUsuarios(Model model, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    UsuarioAction usuarioAction = new UsuarioAction();
    Bitacora bitacora1 = new BitacoraCSV(usuario);
    Bitacora bitacora2 = new BitacoraXML(usuario);
    Bitacora bitacora3 = new BitacoraTramaPlana(usuario);
    bitacora1.setService(bitacoraService);
    bitacora2.setService(bitacoraService);
    bitacora3.setService(bitacoraService);
    usuarioAction.attach(bitacora1);
    usuarioAction.attach(bitacora2);
    usuarioAction.attach(bitacora3);
    usuarioAction.setAccion("Listar usuarios");
    List<Usuario> usuarios = usuarioService.getAllUsuarios();
    model.addAttribute("usuarios", usuarios);
    return "usuario-listar";
  }
}
