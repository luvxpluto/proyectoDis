package com.web.proyectoDisenno.thirdparty;

import com.web.proyectoDisenno.creationallogic.ChatGPTSingleton;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class Translator {
  private static final String LANGUAGES_URI = "https://libretranslate.com/languages";

  public List<Language> getSupportedLanguages() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Language[]> response = restTemplate.getForEntity(LANGUAGES_URI, Language[].class);
    return Arrays.asList(response.getBody());
  }

  public String translateText(String originalText,String targetLanguage) {
    ChatGPT gpt = ChatGPTSingleton.getInstance();
    JSONObject json = new JSONObject();
    json.put("prompt", "Traduce el texto: ");
    json.put("text", originalText.replace("\n", " "));
    json.put("languageOfTheAnswer", targetLanguage);
    return gpt.obtenerRespuesta(json.toString());
  }
}
