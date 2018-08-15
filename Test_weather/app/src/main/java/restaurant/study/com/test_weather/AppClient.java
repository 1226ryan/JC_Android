package restaurant.study.com.test_weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jc_chu on 2018. 08. 05..
 */

public class AppClient {
    private final static String TAG = AppClient.class.getSimpleName();
    private final static String BASE_URL = "https://api.openweathermap.org";

    private static AppClient instance;

    public static AppClient getInstance() {
        if (instance == null) {
            synchronized (AppClient.class) {
                if (instance == null)
                    instance = new AppClient();
            }
        }
        return instance;
    }

    private AppApiService service;
    private AppClient() {
        OkHttpClient okHttpClient = makeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        service = retrofit.create(AppApiService.class);
    }

    private OkHttpClient makeOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public void getCurrentWeather(Context context, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

//        Call<JsonObject> call = service.getCurrentWeather("Seoul", "7005ccb9308e5d650f41449fb5b7f8e2");
        Call<JsonObject> call = service.getCurrentWeather(
                SharedPreferenceUtil.getInstance(context).getLatitude(),
                SharedPreferenceUtil.getInstance(context).getLongitude(),
                "7005ccb9308e5d650f41449fb5b7f8e2");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.i(TAG, "getCurrentWeather onSuccess response = " + response);
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.i(TAG, "getCurrentWeather onFailure");
                t.printStackTrace();
            }
        });
    }

    public void getFiveDaysWeather(Context context, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

//        Call<JsonObject> call = service.getFiveDaysWeather("Seoul", "7005ccb9308e5d650f41449fb5b7f8e2");
        Call<JsonObject> call = service.getFiveDaysWeather(
                SharedPreferenceUtil.getInstance(context).getLatitude(),
                SharedPreferenceUtil.getInstance(context).getLongitude(),
                "7005ccb9308e5d650f41449fb5b7f8e2");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.i(TAG, "getFiveDaysWeather onSuccess response = " + response);
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.i(TAG, "getFiveDaysWeather onFailure");
                t.printStackTrace();
            }
        });
    }
}
