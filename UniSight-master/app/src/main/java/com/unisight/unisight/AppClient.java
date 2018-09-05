package com.unisight.unisight;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jc_chu on 2018. 08. 05..
 */

public class AppClient {
    private volatile static AppClient instance;
    public static AppClient getInstance() {
        if (instance == null) {
            synchronized (AppClient.class) {
                if (instance == null)
                    instance = new AppClient();
            }
        }
        return instance;
    }

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private AppApiService service = retrofit.create(AppApiService.class);

    public void getWeatherCurrent(final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Call<JsonObject> call = service.getWeather("Seoul", "7005ccb9308e5d650f41449fb5b7f8e2");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}