package com.web.proyectoDisenno.thirdparty;

public class PDFCreatorLenguage extends PDFDecorator{
  private String lenguage;

  public PDFCreatorLenguage(PDF pdfCreator, String lenguage) {
    super(pdfCreator);
    this.lenguage = lenguage;
  }

  @Override
  public byte[] createPdf(String infoUsuario, String imagenUsuario, String infoSentimientos,
                          String imagenNube, String infoIdea, String infoGPT) {

    Translator translator = new Translator();

    String infoSentimientosTranslated = translator.translateText(infoSentimientos, lenguage);
    String infoIdeaTranslated = translator.translateText(infoIdea, lenguage);
    String infoGPTTranslated = translator.translateText(infoGPT, lenguage);

    String infoSentimientosCombined = infoSentimientos + "\n\nTraducción:\n" + infoSentimientosTranslated;
    String infoIdeaCombined = infoIdea + "\n\nTraducción:\n" + infoIdeaTranslated;
    String infoGPTCombined = infoGPT + "\n\nTraducción:\n" + infoGPTTranslated;

    return super.createPdf(infoUsuario, imagenUsuario, infoSentimientosCombined,
            imagenNube, infoIdeaCombined, infoGPTCombined);
  }
}
