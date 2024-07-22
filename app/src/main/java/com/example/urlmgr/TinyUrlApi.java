package com.example.urlmgr;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TinyUrlApi {
    @Headers("Authorization: Bearer DHw29xhfZjQ7R2yVB9f9dnVW5yKpJYtssjkqKMEdakh4aZI6b6tsz8XfFiJN")
    @POST("/create")
    Call<TinyUrlResponse> shortenUrl(@Body TinyUrlRequest tinyUrlRequest);
}