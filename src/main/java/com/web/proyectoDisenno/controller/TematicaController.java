package com.web.proyectoDisenno.controller;

import com.web.proyectoDisenno.model.*;
import com.web.proyectoDisenno.service.BitacoraService;
import com.web.proyectoDisenno.service.TematicaService;
import com.web.proyectoDisenno.thirdparty.CloudinaryManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.List;

@Controller
public class TematicaController {
  private final TematicaService tematicaService;
  private final CloudinaryManager cloudinaryManager = CloudinaryManager.getInstance();
  private final UsuarioAction usuarioAction = new UsuarioAction();
  private BitacoraService bitacoraService;

  @Autowired
  public TematicaController(TematicaService tematicaService, BitacoraService bitacoraService) {
    this.tematicaService = tematicaService;
    this.bitacoraService = bitacoraService;
  }

  @GetMapping("/tematica/registrar")
  public String mostrarRegistrarTematica(Model model) {
    Tematica tematica = new Tematica();
    model.addAttribute("tematica", tematica);
    return "tematica-registrar";
  }

  @PostMapping("/tematica/registrar")
  public String procesarRegistrarTematica(@RequestParam String nombre, @RequestParam String descripcion, HttpSession session, Model model,
                                          @RequestParam("imagen") MultipartFile imagenFile) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    Bitacora bitacora1 = new BitacoraCSV(usuario);
    Bitacora bitacora2 = new BitacoraXML(usuario);
    Bitacora bitacora3 = new BitacoraTramaPlana(usuario);
    bitacora1.setService(bitacoraService);
    bitacora2.setService(bitacoraService);
    bitacora3.setService(bitacoraService);
    usuarioAction.attach(bitacora1);
    usuarioAction.attach(bitacora2);
    usuarioAction.attach(bitacora3);

    Tematica tematicaExistente = tematicaService.getTematicaByNombreAndUsuarioIdentificacion(nombre, usuario.getIdentificacion());
    List<String> allowedMimeTypes = Arrays.asList("image/jpeg", "image/png");
    Path tempFile = null;

    try {
      if (tematicaExistente != null) {
        model.addAttribute("error", "Tematica ya registrada");
        return "tematica-registrar";
      }

      if (!imagenFile.isEmpty() && allowedMimeTypes.contains(imagenFile.getContentType())) {
        // Crear archivo temporal
        tempFile = Files.createTempFile(null, null);
        imagenFile.transferTo(tempFile.toFile());

        // Subir la imagen a Cloudinary
        String imageUrl = cloudinaryManager.uploadImage(tempFile.toFile());

        // Crear y guardar la temática
        Tematica tematica = new Tematica(nombre, descripcion, usuario, imageUrl);
        tematicaService.saveTematica(tematica);
        usuarioAction.setAccion("Registrar tematica");

      } else {
        model.addAttribute("error", "Formato de imagen no permitido");
        model.addAttribute("nombre", nombre);
        model.addAttribute("descripcion", descripcion);
      }
      return "tematica-registrar";
    } catch (Exception e) {
      model.addAttribute("error", "Error al registrar tematica: " + e.getMessage());
      model.addAttribute("nombre", nombre);
      model.addAttribute("descripcion", descripcion);
      return "tematica-registrar";
    } finally {
      // Limpiar el archivo temporal si existe
      if (tempFile != null) {
        try {
          Files.deleteIfExists(tempFile);
        } catch (Exception e) {
          System.err.println("Failed to delete temporary file: " + e.getMessage());
        }
      }
    }
  }

  @GetMapping("/tematica/listar")
  public String listarTematicas(HttpSession session, Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    Bitacora bitacora1 = new BitacoraCSV(usuario);
    Bitacora bitacora2 = new BitacoraXML(usuario);
    Bitacora bitacora3 = new BitacoraTramaPlana(usuario);
    bitacora1.setService(bitacoraService);
    bitacora2.setService(bitacoraService);
    bitacora3.setService(bitacoraService);
    usuarioAction.attach(bitacora1);
    usuarioAction.attach(bitacora2);
    usuarioAction.attach(bitacora3);
    usuarioAction.setAccion("Listar tematicas");
    model.addAttribute("tematicas", tematicaService.getTematicasByUsuarioIdentificacion(usuario.getIdentificacion()));
    return "tematica-listar";
  }
}
