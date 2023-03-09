package com.example.prolist;

import java.io.IOException;

public class Url {
    private String url_open = null;
    public Url(String url){
        this.url_open = url;
    }
    public void setUrl_open() throws IOException {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
    }

}
