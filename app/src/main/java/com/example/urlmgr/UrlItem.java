package com.example.urlmgr;

public class UrlItem {
    private int id;
    private String name;
    private String longUrl;
    private String shortUrl;
    private String location;

    // Constructor, getters, and setters

    public UrlItem(int id, String name, String longUrl, String shortUrl, String location) {
        this.id = id;
        this.name = name;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.location = location;
    }

    public int getId() {
        return id;
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
