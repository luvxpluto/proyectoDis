package com.web.proyectoDisenno.creationallogic;

import com.web.proyectoDisenno.thirdparty.NubePalabras;
public class NubePalabrasSingleton {
  private static NubePalabras instance = new NubePalabras();

  private NubePalabrasSingleton() {
  }

  public static NubePalabras getInstance() {
    return instance;
  }
}