package com.example.cnwlc.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WeatherApiInterface {
    @Headers({"Accept: application/json","appKey:1b0e34f9-3289-49d4-9c30-053eb5f9d53a"})
    @GET("/weather/current/hourly")
    Call<WeatherRepo> get_Weather_retrofit(@Query("version") int version, @Query("lat") String lat, @Query("lon") String lon);
}
