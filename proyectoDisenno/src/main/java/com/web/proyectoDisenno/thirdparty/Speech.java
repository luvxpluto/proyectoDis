package com.web.proyectoDisenno.thirdparty;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Speech {

  private static Speech instance;
  private final TextToSpeechClient textToSpeechClient;
  private final CloudinaryManager cloudinaryManager = CloudinaryManager.getInstance();

  // Constructor privado para evitar instanciación externa
  private Speech() throws IOException {
    // Asegurarse que las credenciales están configuradas
    System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

    // Inicializar el cliente de Google Text-to-Speech
    textToSpeechClient = TextToSpeechClient.create();
  }

  // Método público estático para obtener la instancia
  public static synchronized Speech getInstance() throws IOException {
    if (instance == null) {
      instance = new Speech();
    }
    return instance;
  }

  public String synthesizeSpeech(String text, String languageCode) throws IOException {
    // Configuración de la solicitud a Google Cloud Text-to-Speech
    SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
    VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
            .setLanguageCode(languageCode)
            .setSsmlGender(SsmlVoiceGender.NEUTRAL)
            .build();
    AudioConfig audioConfig = AudioConfig.newBuilder()
            .setAudioEncoding(AudioEncoding.MP3) // Formato de audio
            .build();

    // Sintetizar el audio
    SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
    ByteString audioContents = response.getAudioContent();

    // Crear un archivo temporal para guardar el audio
    File tempFile = File.createTempFile("speech", ".mp3");
    try (OutputStream out = new FileOutputStream(tempFile)) {
      out.write(audioContents.toByteArray());
      // Subir el archivo de audio a Cloudinary y obtener la URL
      return cloudinaryManager.uploadSpeech(tempFile);
    } finally {
      // Asegurarse de eliminar el archivo temporal después de subirlo
      if (tempFile.exists()) {
        tempFile.delete();
      }
    }
  }
}
