package com.web.proyectoDisenno.controller;

import com.web.proyectoDisenno.model.Usuario;
import com.web.proyectoDisenno.service.BitacoraService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;

@Controller
public class BitacoraController {
  private final BitacoraService bitacoraService;

  @Autowired
  public BitacoraController(BitacoraService bitacoraService) {
    this.bitacoraService = bitacoraService;
  }

  @GetMapping("/bitacora")
  public String mostrarBitacora(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if(usuario == null) {
      return "redirect:/logout";
    }
    return "bitacoras";
  }

  @PostMapping("/bitacora")
  public String procesarConsultasBitacora(@RequestParam String tipoBitacora, @RequestParam String tipoConsulta,Model model, HttpSession session){
    session.setAttribute("tipoBitacora",tipoBitacora);
    switch(tipoConsulta){
      case "consultaHoy":
        return mostrarConsulta1(model, session, tipoBitacora);
      case "consultaTiempo":
        return mostrarConsulta2();
      case "consultaGeneral":
        return mostrarConsulta3(model, tipoBitacora);
      case "consultaUsuario":
        return mostrarConsulta4();
      default:
        return "Tipo de consulta no soportado";
    }
  };

  @GetMapping("/bitacora/consulta1")
  public String mostrarConsulta1(Model model, HttpSession session, String tipoBitacora){
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String consulta = bitacoraService.getBitacorasByTipoAndFechaHoy(tipoBitacora,usuario.getIdentificacion());
    model.addAttribute("bitacora",consulta);
    return "bitacora-consulta1";
  }

  @GetMapping("/bitacora/consulta2")
  public String mostrarConsulta2(){
    return "bitacora-consulta2";
  }

  @PostMapping("/bitacora/consulta2")
  public String procesarConsulta2(@RequestParam LocalTime horaInicio, @RequestParam LocalTime horaFin, Model model, HttpSession session){
    String tipoBitacora = (String) session.getAttribute("tipoBitacora");
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String consulta = bitacoraService.getBitacorasEntreHorasPorTipo(horaInicio,horaFin,tipoBitacora,usuario.getIdentificacion());
    model.addAttribute("bitacora",consulta);
    return "bitacora-consulta2";
  }

  @GetMapping("/bitacora/consulta3")
  public String mostrarConsulta3(Model model, String tipoBitacora){
    String consulta = bitacoraService.getBitacorasByTipo(tipoBitacora);
    model.addAttribute("bitacora",consulta);
    return "bitacora-consulta3";
  }

  @GetMapping("/bitacora/consulta4")
  public String mostrarConsulta4(){
    return "bitacora-consulta4";
  }

  @PostMapping("/bitacora/consulta4")
  public String procesarConsulta4(@RequestParam String identificacion, Model model, HttpSession session){
    String tipoBitacora = (String) session.getAttribute("tipoBitacora");
    String consulta = bitacoraService.getBitacorasByTipoAndUsuarioIdentificacion(tipoBitacora,identificacion);
    model.addAttribute("bitacora",consulta);
    return "bitacora-consulta4";
  }


}
