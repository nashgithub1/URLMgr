package com.example.urlmgr;

public class UrlItem {
    private String name;
    private String longUrl;
    private String shortUrl;
    private String location;

    // Constructor, getters, and setters

    public UrlItem(String name, String longUrl, String shortUrl, String location) {
        this.name = name;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }
    public String getLocation() {
        return location;
    }
}
