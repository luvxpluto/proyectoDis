package com.web.proyectoDisenno.model;

import java.util.ArrayList;
import java.util.List;

public class UsuarioAction {
  private String accion;
  private final List<Bitacora> bitacoras = new ArrayList<>();

  public UsuarioAction() {
  }

  public void setAccion(String accion) {
    this.accion = accion;
    notifyObservers();
  }

  public void attach(Bitacora bitacora) {
    bitacoras.add(bitacora);
  }

  private void notifyObservers() {
    for (Bitacora bitacora : bitacoras) {
      System.out.println("Contador de bitacoras");
      bitacora.registrar(accion);
    }
  }
}
