package com.web.proyectoDisenno.thirdparty;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.activation.DataHandler;
import java.util.Properties;

import org.apache.commons.validator.routines.EmailValidator;

public class CuentaCorreo {
  private final String usuario = System.getenv("USER_MAIL");
  private final String clave = System.getenv("PW_MAIL");
  private final Properties propiedades;

  // Instance of the singleton class
  private static CuentaCorreo instance = null;

  // Private constructor to prevent instantiation
  private CuentaCorreo(){
    propiedades = new Properties();
    String servidor = "smtp.gmail.com";
    propiedades.put("mail.smtp.host", servidor);
    String puerto = "587";
    propiedades.put("mail.smtp.port", puerto);
    propiedades.put("mail.smtp.auth","true");
    propiedades.put("mail.smtp.starttls.enable","true");
  }

  // Method to get the singleton instance
  public static CuentaCorreo getInstance() {
    if (instance == null) {
      instance = new CuentaCorreo();
    }
    return instance;
  }

  public void enviarCorreo(String destinatario, String tituloCorreo, String cuerpo, byte[] pdfBytes) {
    Session sesion = abrirSesion();
    System.out.println("Print CUENTA CORREO destinatario: "+destinatario);
    System.out.println("Print CUENTA CORREO usuario: "+usuario);
    try {
      Message message = new MimeMessage(sesion);
      message.setFrom(new InternetAddress(usuario));
      message.setRecipients(
              Message.RecipientType.TO,
              InternetAddress.parse(destinatario)
      );
      message.setSubject(tituloCorreo);

      // Crear el cuerpo del mensaje
      MimeBodyPart mensajeParte = new MimeBodyPart();
      mensajeParte.setText(cuerpo);

      // Crear la parte del adjunto PDF
      MimeBodyPart adjuntoParte = new MimeBodyPart();
      ByteArrayDataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");
      adjuntoParte.setDataHandler(new DataHandler(dataSource));
      adjuntoParte.setFileName("documento.pdf");

      // Crear el contenido multipart
      MimeMultipart contenido = new MimeMultipart();
      contenido.addBodyPart(mensajeParte);
      contenido.addBodyPart(adjuntoParte);

      // Establecer el contenido del mensaje
      message.setContent(contenido);

      // Enviar el mensaje
      Transport.send(message);

    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  private Session abrirSesion(){
    return Session.getInstance(propiedades,
            new javax.mail.Authenticator(){
              protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(usuario,clave);
              }
            });
  }

  public boolean verificarDestinatario(String pDestinatario){
    return EmailValidator.getInstance().isValid(pDestinatario);
  }
}
