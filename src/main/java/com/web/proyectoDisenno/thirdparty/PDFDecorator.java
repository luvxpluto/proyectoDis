package com.web.proyectoDisenno.thirdparty;

public class PDFDecorator implements PDF{
  protected PDF pdfCreator;

  public PDFDecorator(PDF pdfCreator) {
    this.pdfCreator = pdfCreator;
  }

  @Override
  public byte[] createPdf(String infoUsuario, String imagenUsuario, String infoSentimientos, String imagenNube, String infoIdea, String infoGPT) {
    return pdfCreator.createPdf(infoUsuario, imagenUsuario, infoSentimientos, imagenNube, infoIdea, infoGPT);
  }
}
