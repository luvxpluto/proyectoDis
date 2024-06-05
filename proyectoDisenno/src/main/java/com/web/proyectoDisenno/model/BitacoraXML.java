package com.web.proyectoDisenno.model;

public class BitacoraXML extends  Bitacora{
    @Override
    public void registrar(String accion) {
        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("<registro>\n");
        cuerpo.append("  <usuario>").append(usuario.getNombreCompleto()).append("</usuario>\n");
        cuerpo.append("  <accion>").append(accion).append("</accion>\n");
        cuerpo.append("  <fecha>").append(getFecha().toString()).append("</fecha>\n");
        cuerpo.append("  <ip>").append(getIP()).append("</ip>\n");
        cuerpo.append("  <sistemaOperativo>").append(getSistemaOperativo()).append("</sistemaOperativo>\n");
        cuerpo.append("</registro>");

        descripcion = cuerpo.toString();
        // BD
    }
}
