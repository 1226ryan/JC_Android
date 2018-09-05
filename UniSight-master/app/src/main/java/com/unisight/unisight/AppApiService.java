package com.unisight.unisight;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jc_chu on 2018. 08. 05..
 */

public interface AppApiService {
    @GET("/data/2.5/weather")
    Call<JsonObject> getWeather(@Query("q") String cityName, @Query("appid") String apiKey);

    @GET("data/2.5/forecast")
    Call<JsonObject> getFiveWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);
}