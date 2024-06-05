package com.web.proyectoDisenno.controller;

import com.web.proyectoDisenno.creationallogic.CuentaCorreoSingleton;
import com.web.proyectoDisenno.model.Texto;
import com.web.proyectoDisenno.model.Usuario;
import com.web.proyectoDisenno.service.TematicaService;
import com.web.proyectoDisenno.service.TextoService;
import com.web.proyectoDisenno.thirdparty.ChatGPT;
import com.web.proyectoDisenno.thirdparty.CuentaCorreo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TextoController {
  private final TextoService textoService;
  private final TematicaService tematicaService;

  @Autowired
  public TextoController(TextoService textoService, TematicaService tematicaService) {
    this.textoService = textoService;
    this.tematicaService = tematicaService;
  }

  @GetMapping("/texto/registrar")
  public String mostrarRegistrarTexto(HttpSession session, Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
      return "redirect:/logout";
    }
    model.addAttribute("tematicas", tematicaService.getTematicasByUsuarioIdentificacion(usuario.getIdentificacion()));
    return "texto-registrar";
  }

  @PostMapping("/texto/registrar")
  public String procesarRegistrarTexto(@RequestParam String tematica,@RequestParam String contenido, HttpSession session){
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    textoService.saveTexto(new Texto(contenido, tematicaService.getTematicaByNombreAndUsuarioIdentificacion(tematica, usuario.getIdentificacion())));
    return "redirect:/texto/registrar";
  }

  @GetMapping("/texto/consultar")
  public String mostrarConsultarTexto(HttpSession session, Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
      return "redirect:/logout";
    }
    model.addAttribute("tematicas", tematicaService.getTematicasByUsuarioIdentificacion(usuario.getIdentificacion()));
    return "texto-consultar";
  }

  @PostMapping("/texto/consultar")
  public String procesarConsultarTexto(@RequestParam String tematica, Model model, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
      return "redirect:/logout";
    }
    model.addAttribute("tematicas", tematicaService.getTematicasByUsuarioIdentificacion(usuario.getIdentificacion()));
    model.addAttribute("textos", textoService.getTextosByTematica(tematica));
    return "texto-consultar";
  }

  @GetMapping("/texto/detalles/{textoId}")
  public String mostrarDetallesTexto(@PathVariable Long textoId, Model model) {
    model.addAttribute("texto", textoService.getTextoById(textoId));
    return "texto-detalles";
  }

  @GetMapping("/texto/sentimientos/{textoId}")
  public String mostrarSentimientosTexto(@PathVariable Long textoId, Model model) {
    Texto texto = textoService.getTextoById(textoId);
    model.addAttribute("texto", texto);
    model.addAttribute("sentimientos", texto.analizarSentimientos());
    return "texto-sentimientos";
  }

  @GetMapping("/texto/idea/{textoId}")
  public String mostrarIdeaTexto(@PathVariable Long textoId, Model model) {
    Texto texto = textoService.getTextoById(textoId);
    model.addAttribute("texto", texto);
    model.addAttribute("idea", texto.generarIdeaPrincipal());
    return "texto-idea";
  }

  @GetMapping("/texto/gpt/{textoId}")
  public String mostrarGPTTexto(@PathVariable Long textoId, Model model) {
    Texto texto = textoService.getTextoById(textoId);
    model.addAttribute("texto", texto);
    model.addAttribute("gpt", texto.consultarIdea());
    return "texto-gpt";
  }

  @GetMapping("/texto/cloud/{textoId}")
  public String mostrarNubeTexto(@PathVariable Long textoId, Model model) {
    Texto texto = textoService.getTextoById(textoId);
    String nubeUrl = texto.generarNubePalabras();
    model.addAttribute("texto", texto);
    model.addAttribute("url", nubeUrl);
    return "texto-nube";
  }

  @GetMapping("/texto/pdf/{textoId}")
  public String enviarCorreo(@PathVariable Long textoId, HttpSession session,Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    Texto texto = textoService.getTextoById(textoId);
    CuentaCorreo correo = CuentaCorreoSingleton.getInstance();
    model.addAttribute("texto", texto);
    if(correo.verificarDestinatario(usuario.getCorreo())){
      texto.generarPdf(usuario.getIdentificacion(), usuario.getNombreCompleto(), usuario.getCorreo(), usuario.getNumeroTelefono(),usuario.getUrlFoto());
      System.out.println("Print controlador: "  + usuario.getCorreo());
      model.addAttribute("message","Correo enviado con éxito");
      return "texto-correo";
    }
    model.addAttribute("error","La dirección de correo del usuario no es valida");
    return "texto-correo";
  }

  @GetMapping("/texto/speech/{textoId}")
  public String mostrarSpeech(@PathVariable Long textoId, Model model, HttpSession session) {
    Texto texto = textoService.getTextoById(textoId);
    String speechUrl = texto.generarAudio();
    model.addAttribute("texto", texto);
    model.addAttribute("speech", speechUrl);
    return "texto-speech";
  }

}
