package com.web.proyectoDisenno.thirdparty;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PDFCreator implements PDF{
  public PDFCreator() {}

  public byte[] createPdf(String infoUsuario, String imagenUsuario, String infoSentimientos,
                          String imagenNube, String infoIdea, String infoGPT) {
    Document document = new Document();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      PdfWriter.getInstance(document, byteArrayOutputStream);
      document.open();

      // Información del usuario
      document.add(new Paragraph("Información del Usuario: \n" + infoUsuario));
      Image userImage = Image.getInstance(new URL(imagenUsuario));
      userImage.scaleToFit(200, 200); // Ajustar el tamaño de la imagen del usuario
      document.add(userImage);

      // Sentimientos
      document.add(new Paragraph("Análisis de Sentimientos: \n" + infoSentimientos));

      // Nube de palabras
      document.add(new Paragraph("Nube de Palabras: "));
      Image nubeImage = Image.getInstance(new URL(imagenNube));
      nubeImage.scaleToFit(300, 300); // Ajustar el tamaño de la imagen de la nube de palabras
      document.add(nubeImage);

      // Idea principal
      document.add(new Paragraph("Idea Principal: \n" + infoIdea));

      // GPT Response
      document.add(new Paragraph("Respuesta GPT acerca de la idea principal: \n" + infoGPT));

      document.close(); // Cierra el documento
      return byteArrayOutputStream.toByteArray(); // Retorna el contenido del PDF como un arreglo de bytes
    } catch (DocumentException | MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null; // En caso de error retorna null
  }
}
