package com.web.proyectoDisenno.thirdparty;

public interface PDF {
  byte[] createPdf(String infoUsuario, String imagenUsuario, String infoSentimientos,
                    String imagenNube, String infoIdea, String infoGPT);
}
