package com.web.proyectoDisenno.creationallogic;

import com.web.proyectoDisenno.thirdparty.Speech;

import java.io.IOException;

public class SpeechSingleton {
    private static Speech instance;

    static {
        try {
            instance = new Speech();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SpeechSingleton() {
    }

    public static synchronized Speech getInstance() {
        return instance;
    }
}
