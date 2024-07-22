package com.example.urlmgr;

public class TinyUrlResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String tiny_url;

        public String getTinyUrl() {
            return tiny_url;
        }

        public void setTinyUrl(String tinyUrl) {
            this.tiny_url = tinyUrl;
        }
    }
}