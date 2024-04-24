package com.web.proyectoDisenno.thirdparty;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

public class Speech {

  private static Speech instance;
  private final TextToSpeechClient textToSpeechClient;
  private final CloudinaryManager cloudinaryManager = CloudinaryManager.getInstance();

  // Constructor privado para evitar instanciación externa
  private Speech() throws IOException {
    // Decodificar las credenciales desde la variable de entorno
    String base64Credentials = System.getenv("GOOGLE_CREDENTIALS_BASE64");
    byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
    try (InputStream credentialsStream = new ByteArrayInputStream(decodedBytes)) {
      GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
      TextToSpeechSettings settings = TextToSpeechSettings.newBuilder()
              .setCredentialsProvider(() -> credentials)
              .build();

      // Inicializar el cliente de Google Text-to-Speech con las credenciales configuradas
      textToSpeechClient = TextToSpeechClient.create(settings);
    }
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
