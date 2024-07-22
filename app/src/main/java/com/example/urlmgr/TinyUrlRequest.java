package com.example.urlmgr;

public class TinyUrlRequest {
    private String url;
    private String domain;

    public TinyUrlRequest(String url) {
        this.url = url;
        this.domain = "tinyurl.com"; // You can change this if you want to use a custom domain
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}