package com.web.proyectoDisenno.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@NoArgsConstructor
@DiscriminatorValue("TramaPlana")
public class BitacoraTramaPlana extends Bitacora{

  public BitacoraTramaPlana(Usuario usuario) {
    super(usuario);
  }
  @Override
  public void registrar(String accion) {
    fecha = LocalDateTime.now().toLocalDate();
    descripcion = "Accion" + accion.replace(" ", "") +
            "Usuario" + usuario.getNombreCompleto().replace(" ", "") +
            "Fecha" + fecha +
            "IP" + getIP() +
            "SistemaOperativo" + System.getProperty("os.name").replace(" ", "") +
            "Pais" + Locale.getDefault().getDisplayCountry().replace(" ", "");
    service.saveBitacora(this);
  }
}