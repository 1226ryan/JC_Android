package restaurant.study.com.test_weather;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jc_chu on 2018. 08. 11..
 */

public interface AppApiService {
    @GET("/data/2.5/weather")
    Call<JsonObject> getCurrentWeather(@Query("q") String cityName, @Query("appid") String apiKey);

    @GET("/data/2.5/forecast")
    Call<JsonObject> getFiveDaysWeather(@Query("q") String cityName, @Query("appid") String apiKey);
//            (@Query("lat") String lat, @Query("lon") String lon);
}
