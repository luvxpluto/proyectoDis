package com.web.proyectoDisenno.creationallogic;

import com.web.proyectoDisenno.thirdparty.CloudinaryManager;

public class CloudinaryManagerSingleton {
    private static CloudinaryManager instance = new CloudinaryManager();


    private CloudinaryManagerSingleton() {
    }

    public static synchronized CloudinaryManager getInstance() {
        return instance;
    }
}
