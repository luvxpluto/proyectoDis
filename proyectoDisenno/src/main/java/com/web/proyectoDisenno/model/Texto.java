package com.web.proyectoDisenno.model;

import com.web.proyectoDisenno.thirdparty.ChatGPT;
import com.web.proyectoDisenno.thirdparty.CuentaCorreo;
import com.web.proyectoDisenno.thirdparty.NubePalabras;
import com.web.proyectoDisenno.thirdparty.PDFCreator;
import com.web.proyectoDisenno.thirdparty.Speech;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "textos")
public class Texto implements ITexto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;
  @Column(columnDefinition = "TEXT")
  @Getter
  private String contenido;
  @Getter
  private LocalDateTime fechaHora;
  @Getter
  private int cantidadPalabras;

  @ManyToOne
  @JoinColumn(name = "tematica_nombre", nullable = false)
  private Tematica tematica;

  public Texto(String contenido, Tematica tematica) {
    this.contenido = contenido;
    this.fechaHora = LocalDateTime.now();
    this.tematica = tematica;
    this.cantidadPalabras = calcularCantidadPalabras();
  }

  private int calcularCantidadPalabras() {
    if (contenido.isEmpty()) {
      return 0;
    }
    String[] palabras = contenido.split("\\s+");
    return palabras.length;
  }
  public String primerasTreintaPalabras() {
    String[] palabras = contenido.split("\\s+");  // Divide el texto por uno o más espacios
    StringBuilder resultado = new StringBuilder();

    for (int i = 0; i < Math.min(30, palabras.length); i++) {
      resultado.append(palabras[i]).append(" ");
    }
    return resultado.toString().trim();
  }

  public String obtenerFechaFormateada() {
    return fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
  }

  public String analizarSentimientos() {
    ChatGPT gpt = ChatGPT.getInstance();
    JSONObject json = new JSONObject();
    json.put("prompt", "Extrae un conjunto de sentimientos relacionados con el texto");
    json.put("text", contenido.replace("\n", " "));
    json.put("languageOfTheAnswer", consultarIdioma());
    return gpt.obtenerRespuesta(json.toString());
  }

  public String generarNubePalabras(){
    NubePalabras nube = NubePalabras.getInstance();
    try {
      return nube.generar(contenido);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String generarIdeaPrincipal() {
    ChatGPT gpt = ChatGPT.getInstance();
    JSONObject json = new JSONObject();
    json.put("prompt", "Extrae idea principal del texto");
    json.put("text", contenido.replace("\n", " "));
    json.put("languageOfTheAnswer", consultarIdioma());
    return gpt.obtenerRespuesta(json.toString());
  }



  public String consultarIdea() {
    ChatGPT gpt = ChatGPT.getInstance();
    JSONObject json = new JSONObject();
    json.put("prompt", "Dame información sobre la idea principal del texto (Expande la idea)");
    json.put("text", generarIdeaPrincipal().replace("\n", " "));
    json.put("languageOfTheAnswer", consultarIdioma());
    return gpt.obtenerRespuesta(json.toString());
  }

  public void generarPdf(String indentificacion, String nombre, String correo, String numero, String imagenUsuario) {
    String infoUsuario = "Identificación: " + indentificacion + "\n" +
            "Nombre: " + nombre + "\n" +
            "Correo: " + correo + "\n" +
            "Número: " + numero + "\n";

    String infoSentimientos = "Prueba";
    String imagenNube = generarNubePalabras();
    String infoIdea = generarIdeaPrincipal();
    String infoGPT = consultarIdea();

    PDFCreator pdf = PDFCreator.getInstance();
    byte[] pdfBytes = pdf.createPdf(infoUsuario, imagenUsuario, infoSentimientos, imagenNube, infoIdea, infoGPT);

    CuentaCorreo cuentaCorreo = CuentaCorreo.getInstance();
    System.out.println("Print texto: " + correo);
    cuentaCorreo.enviarCorreo(correo, "Informe de Análisis de Texto", "Se adjunta el informe de análisis de texto", pdfBytes);
  }

  private String consultarIdioma() {
    ChatGPT gpt = ChatGPT.getInstance();
    JSONObject json = new JSONObject();
    json.put("prompt", "Dame el idioma del texto (Responde solamente con el idioma)");
    json.put("text", contenido.replace("\n", " "));

    String respuesta = gpt.obtenerRespuesta(json.toString()).toLowerCase();
    if (respuesta.contains("inglés")) {
      return "en-US";
    } else if (respuesta.contains("español")) {
      return "es-US";
    } else {
      return "Unknown";  // Considera devolver un valor por defecto o manejar errores aquí
    }
  }

  public String generarAudio(){
    try{
      Speech speech = Speech.getInstance();
      return speech.synthesizeSpeech(contenido,consultarIdioma());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
