package com.web.proyectoDisenno.creationallogic;

import com.web.proyectoDisenno.thirdparty.PDFCreator;

public class PDFCreatorSingleton {
    private static PDFCreator instance = new PDFCreator();

    private PDFCreatorSingleton() {
    }

    public static synchronized PDFCreator getInstance() {
        return instance;
    }
}
