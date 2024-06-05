package com.web.proyectoDisenno.creationallogic;

import com.web.proyectoDisenno.thirdparty.CuentaCorreo;

public class CuentaCorreoSingleton {
    private static CuentaCorreo instance = new CuentaCorreo();

    private CuentaCorreoSingleton() {
    }

    public static synchronized CuentaCorreo getInstance() {
        return instance;
    }
}
