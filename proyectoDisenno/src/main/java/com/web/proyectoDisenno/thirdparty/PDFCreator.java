package com.web.proyectoDisenno.thirdparty;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    try (PDDocument document = new PDDocument()) {
      PDPage page = new PDPage(PDRectangle.A4);
      document.addPage(page);
      try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
        PDImageXObject userImage = PDImageXObject.createFromFile(new URL(imagenUsuario).getPath(), document);
        PDImageXObject nubeImage = PDImageXObject.createFromFile(new URL(imagenNube).getPath(), document);

        // Información del usuario y su imagen
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(25, 750);
        contentStream.showText("Información del Usuario: ");
        contentStream.newLine();
        contentStream.showText(infoUsuario);
        contentStream.endText();
        contentStream.drawImage(userImage, 25, 620, 200, 200);

        // Análisis de sentimientos
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 400);
        contentStream.showText("Análisis de Sentimientos: ");
        contentStream.newLine();
        contentStream.showText(infoSentimientos);
        contentStream.endText();

        // Nube de palabras
        contentStream.drawImage(nubeImage, 25, 100, 300, 300);

        // Idea principal y respuesta GPT
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 50);
        contentStream.showText("Idea Principal: ");
        contentStream.newLine();
        contentStream.showText(infoIdea);
        contentStream.newLine();
        contentStream.showText("Respuesta GPT acerca de la idea principal: ");
        contentStream.newLine();
        contentStream.showText(infoGPT);
        contentStream.endText();
      }

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      document.save(out);
      return out.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
