package com.web.proyectoDisenno.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@NoArgsConstructor
@DiscriminatorValue("CSV")
public class BitacoraCSV extends Bitacora{

  public BitacoraCSV(Usuario usuario) {
    super(usuario);
  }

  @Override
  public void registrar(String accion) {
    fecha = LocalDateTime.now().toLocalDate();
    hora = LocalDateTime.now().toLocalTime();
    descripcion = accion + "," +
            usuario.getNombreCompleto() + "," +
            fecha + "," +
            hora + "," +
            getIP() + "," +
            System.getProperty("os.name") + "," +
            Locale.getDefault().getDisplayCountry();
    service.saveBitacora(this);
  }
}
