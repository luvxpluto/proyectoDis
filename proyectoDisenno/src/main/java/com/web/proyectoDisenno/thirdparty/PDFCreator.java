package com.web.proyectoDisenno.thirdparty;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.ByteArrayOutputStream;
import java.net.URL;

public class PDFCreator {
  private static PDFCreator instance; // Singleton instance

  // Private constructor to prevent instantiation
  private PDFCreator() {}

  // Public method to get the instance
  public static synchronized PDFCreator getInstance() {
    if (instance == null) {
      instance = new PDFCreator();
    }
    return instance;
  }

  public byte[] createPdf(String infoUsuario, String imagenUsuario, String infoSentimientos,
                          String imagenNube, String infoIdea, String infoGPT) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      PdfWriter writer = new PdfWriter(byteArrayOutputStream);
      PdfDocument pdf = new PdfDocument(writer);
      Document document = new Document(pdf);

      // Información del usuario
      document.add(new Paragraph("Información del Usuario: \n" + infoUsuario));
      Image userImage = new Image(ImageDataFactory.create(new URL(imagenUsuario)));
      userImage.scaleToFit(200, 200); // Ajustar el tamaño de la imagen del usuario
      document.add(userImage);

      // Sentimientos
      document.add(new Paragraph("Análisis de Sentimientos: \n" + infoSentimientos));

      // Nube de palabras
      document.add(new Paragraph("Nube de Palabras: "));
      Image nubeImage = new Image(ImageDataFactory.create(new URL(imagenNube)));
      nubeImage.scaleToFit(300, 300); // Ajustar el tamaño de la imagen de la nube de palabras
      document.add(nubeImage);

      // Idea principal
      document.add(new Paragraph("Idea Principal: \n" + infoIdea));

      // GPT Response
      document.add(new Paragraph("Respuesta GPT acerca de la idea principal: \n" + infoGPT));

      // Cierra el documento
      document.close();

      // Retorna el contenido del PDF como un arreglo de bytes
      return byteArrayOutputStream.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null; // En caso de error retorna null
  }
}
