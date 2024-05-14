package com.example.urlmgr;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BitlyApi {
    @POST("shorten")
    Call<ShortenResponse> shortenUrl(@Body ShortenRequest shortenRequest);
}
