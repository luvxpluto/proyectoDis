package com.web.proyectoDisenno.thirdparty;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.model.Image;
import com.web.proyectoDisenno.model.Usuario;
import com.web.proyectoDisenno.service.UsuarioService;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Base64;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import org.springframework.stereotype.Service;

@Service
public class FaceRecognitionService {
  private UsuarioService usuarioService; //Servicio del usuario para obtener la información del usuario
  private AmazonRekognition rekognitionClient;


  public FaceRecognitionService(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
    BasicAWSCredentials awsCreds = new BasicAWSCredentials(System.getenv("ACCESS_KEY"), System.getenv("SECRET_KEY"));
    rekognitionClient = AmazonRekognitionClientBuilder.standard().
            withCredentials(new AWSStaticCredentialsProvider(awsCreds)).
            withRegion(Regions.US_EAST_1).build();
  }

  public Usuario authenticateUserFaceId(String base64Image){
    List<String> urls = usuarioService.getUsuariosUrlImage();
    byte[] image = decodeBase64ToBytes(base64Image);
    ByteBuffer sourceImageBuffer = ByteBuffer.wrap(image);
    Image sourceImage = new Image().withBytes(sourceImageBuffer);

    for (String url : urls) {
      try {
        InputStream targetStream = new URL(url).openStream();
        ByteBuffer targetImageBuffer = ByteBuffer.wrap(targetStream.readAllBytes());
        Image targetImage = new Image().withBytes(targetImageBuffer);

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(sourceImage)
                .withTargetImage(targetImage)
                .withSimilarityThreshold(70F); // Set similarity threshold to 70%

        CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);

        List<CompareFacesMatch> faceMatches = compareFacesResult.getFaceMatches();
        for (CompareFacesMatch match : faceMatches) {
          if (match.getSimilarity() >= 70) {
            // Return the user corresponding to the matched image
            return usuarioService.getUsuarioByUrlFoto(url);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return null; // Return null if no match found
  }

  private byte[] decodeBase64ToBytes(String base64Image) {
    if (base64Image.startsWith("data:image/jpeg;base64,")) {
      base64Image = base64Image.substring("data:image/jpeg;base64,".length());
    }
    base64Image = base64Image.replaceAll("\\s+", "");
    return Base64.getDecoder().decode(base64Image);
  }
}
