package com.web.proyectoDisenno.thirdparty;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryManager {

  private static CloudinaryManager instance; // Instancia Singleton
  private final Cloudinary cloudinary;

  // Constructor privado para prevenir instanciación directa
  private CloudinaryManager() {
    String cloudName = System.getenv("NAME_CLOUD");
    String apiKey = System.getenv("KEY_CLOUD");
    String apiSecret = System.getenv("SECRET_CLOUD");

    // Inicializar Cloudinary con la configuración
    Map<String, Object> config = new HashMap<>();
    config.put("cloud_name", cloudName);
    config.put("api_key", apiKey);
    config.put("api_secret", apiSecret);
    cloudinary = new Cloudinary(config);
  }

  // Método público estático para obtener la instancia
  public static synchronized CloudinaryManager getInstance() {
    if (instance == null) {
      instance = new CloudinaryManager();
    }
    return instance;
  }

  public String uploadImage(File imageFile) throws IOException {
    Map<?, ?> result = cloudinary.uploader().upload(imageFile, ObjectUtils.emptyMap());
    return (String) result.get("secure_url");
  }

  public String uploadSpeech(File audioFile) throws IOException {
    // Configurar parámetros específicos para audio si es necesario
    Map<String, Object> options = new HashMap<>();
    options.put("resource_type", "raw"); // Asegurarse de usar raw para archivos no imagen/video

    Map<?, ?> result = cloudinary.uploader().upload(audioFile, options);
    return (String) result.get("secure_url");
  }
}
