package com.web.proyectoDisenno.model;

import java.time.LocalDateTime;
import java.util.Locale;

public class BitacoraCSV extends Bitacora{
    @Override
    public void registrar(String accion) {
        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("accion,usuario,fecha,ip,sistemaOperativo,pais\n");
        cuerpo.append(accion).append(",");
        cuerpo.append(usuario.getNombreCompleto());
        cuerpo.append(LocalDateTime.now().toString()).append(",");
        cuerpo.append(getIP()).append(",");
        cuerpo.append(System.getProperty("os.name")).append(",");
        cuerpo.append(Locale.getDefault().getDisplayCountry()).append(",");

        this.descripcion = cuerpo.toString();
        //BD
    }
}