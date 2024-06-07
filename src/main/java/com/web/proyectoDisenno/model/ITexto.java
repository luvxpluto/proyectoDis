package com.web.proyectoDisenno.model;

import java.io.IOException;

public interface ITexto {
  String analizarSentimientos();
  String generarNubePalabras() throws IOException;
  String generarIdeaPrincipal();
  String consultarIdea();
  void generarPdf(String indentificacion, String nombre, String Correo, String numero, String imagenUsuario,String idioma);
  String generarAudio();
}