package com.web.proyectoDisenno.thirdparty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang3.StringEscapeUtils;

public class ChatGPT {
  private final String apiKey = System.getenv("KEY_GPT");
  private final String model = "gpt-3.5-turbo";
  private final String url = "https://api.openai.com/v1/chat/completions";

  public ChatGPT() {}


  public String obtenerRespuesta(String prompt) {
    try {
      URL obj = new URL(this.url);
      HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Authorization", "Bearer " + this.apiKey);
      connection.setRequestProperty("Content-Type", "application/json");

      // Cuerpo de la solicitud
      String body = "{\"model\": \"" + this.model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + StringEscapeUtils.escapeJava(prompt) + "\"}]}";
      connection.setDoOutput(true);
      OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

      writer.write(body);
      writer.flush();
      writer.close();

      // Respuesta de ChatGPT
      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        response.append(line).append(System.lineSeparator()); // Agregar el salto de línea
      }
      br.close();

      // Llama al método para extraer el mensaje
      return extractMessageFromJSONResponse(response.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String extractMessageFromJSONResponse(String respuesta) {
    int start = respuesta.indexOf("content") + 11;
    int end = respuesta.indexOf("\"", start);
    return respuesta.substring(start, end);
  }
}
