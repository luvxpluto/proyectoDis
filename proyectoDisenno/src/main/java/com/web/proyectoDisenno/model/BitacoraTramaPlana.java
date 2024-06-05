package com.web.proyectoDisenno.model;

import java.time.LocalDateTime;
import java.util.Locale;

public class BitacoraTramaPlana extends Bitacora{
    @Override
    public void registrar(String accion) {
        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("Accion").append(accion.replace(" ", ""));
        cuerpo.append("Usuario").append(usuario.getNombreCompleto().replace(" ", ""));
        cuerpo.append("Fecha").append(LocalDateTime.now().toString());
        cuerpo.append("IP").append(getIP());
        cuerpo.append("SistemaOperativo").append(System.getProperty("os.name").replace(" ", ""));
        cuerpo.append("Pais").append(Locale.getDefault().getDisplayCountry().replace(" ", ""));

        descripcion = cuerpo.toString();
        //BD
    }
}
