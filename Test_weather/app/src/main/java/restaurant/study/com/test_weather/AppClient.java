package restaurant.study.com.test_weather;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClient {
    private static final String TAG = AppClient.class.getSimpleName();
    private static final int RETRY_COUNT = 3;           // 재시도 횟수
    private static final int RETRY_DELAY_TIME = 1000;   // 재시도 딜레이 타임
    private static final String BASE_URL = "http://api.openweathermap.org";

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
            .baseUrl(BASE_URL)
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
