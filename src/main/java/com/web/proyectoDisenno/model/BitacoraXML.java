package com.web.proyectoDisenno.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@DiscriminatorValue("XML")
public class BitacoraXML extends Bitacora{
  public BitacoraXML(Usuario usuario) {
    super(usuario);
  }
  @Override
  public void registrar(String accion) {
    fecha = LocalDateTime.now().toLocalDate();
    hora = LocalDateTime.now().toLocalTime();
    descripcion = "<registro>\n" +
            "  <usuario>" + usuario.getNombreCompleto() + "</usuario>\n" +
            "  <accion>" + accion + "</accion>\n" +
            "  <fecha>" + fecha + "</fecha>\n" +
            "  <hora>" + hora + "</hora>\n" +
            "  <ip>" + getIP() + "</ip>\n" +
            "  <sistemaOperativo>" + getSistemaOperativo() + "</sistemaOperativo>\n" +
            "</registro>";
    service.saveBitacora(this);
  }
}