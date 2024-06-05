package com.web.proyectoDisenno.thirdparty;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import com.web.proyectoDisenno.creationallogic.CloudinaryManagerSingleton;

import java.awt.Dimension;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class NubePalabras {
  private WordCloud wordCloud;
  private final CloudinaryManager cloudinaryManager = CloudinaryManagerSingleton.getInstance(); // Asumiendo que CloudinaryManager está en el mismo paquete

  public NubePalabras() {
    initWordCloud(); // Initialize the WordCloud object
  }

  // Method to initialize the WordCloud configuration
  private void initWordCloud() {
    Dimension dimension = new Dimension(600, 600);
    CollisionMode collisionMode = CollisionMode.PIXEL_PERFECT;
    wordCloud = new WordCloud(dimension, collisionMode);
    wordCloud.setPadding(2);
    wordCloud.setBackground(new CircleBackground(300));
    wordCloud.setBackgroundColor(Color.WHITE);
    wordCloud.setColorPalette(new LinearGradientColorPalette(
            Color.decode("#9E7EFA"),
            Color.decode("#3E8AFA"),
            Color.decode("#DB6FDE"), 15, 15));
    wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
    wordCloud.setKumoFont(new KumoFont("Arial", FontWeight.BOLD));
  }

  public String generar(String texto) throws IOException {
    initWordCloud(); // Reset WordCloud before building a new one
    final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
    final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStreamFromText(texto));
    wordCloud.build(wordFrequencies);

    // Crear un archivo temporal
    File tempFile = File.createTempFile("wordcloud", ".png");
    try {
      this.wordCloud.writeToFile(tempFile.getPath());

      // Subir el archivo a Cloudinary y obtener la URL
      return cloudinaryManager.uploadImage(tempFile);
    } finally {
      // Asegúrate de eliminar el archivo temporal
      if (tempFile.exists()) {
        tempFile.delete();
      }
    }
  }

  private InputStream getInputStreamFromText(String texto) {
    return new ByteArrayInputStream(texto.getBytes(StandardCharsets.UTF_8));
  }
}
