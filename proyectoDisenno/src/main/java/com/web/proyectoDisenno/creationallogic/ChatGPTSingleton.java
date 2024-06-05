package com.web.proyectoDisenno.creationallogic;

import com.web.proyectoDisenno.thirdparty.ChatGPT;

public class ChatGPTSingleton {
    private static ChatGPT instance = new ChatGPT();

    private ChatGPTSingleton() {
    }

    public static synchronized ChatGPT getInstance() {
        return instance;
    }
}
