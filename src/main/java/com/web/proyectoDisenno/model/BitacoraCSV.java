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
    descripcion = accion + "," +
            usuario.getNombreCompleto() + "," +
            fecha + "," +
            getIP() + "," +
            System.getProperty("os.name") + "," +
            Locale.getDefault().getDisplayCountry() + ",";
    // Usar el service para pasarle el objeto bitacora y lo almacene en la BD
    service.saveBitacora(this);
  }
}
