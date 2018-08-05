package restaurant.study.com.test_weather;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppApiService {
    @GET("/data/2.5/weather")
    Call<JsonObject> getWeather(@Query("q") String cityName, @Query("appid") String apiKey);
}
