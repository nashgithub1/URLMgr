// UrlModel.java
package com.example.urlmgr;

public class UrlModel {
    private int id;
    private String url;

    public UrlModel(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
