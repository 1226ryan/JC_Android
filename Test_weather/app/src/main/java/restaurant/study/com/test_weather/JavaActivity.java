package restaurant.study.com.test_weather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaActivity extends AppCompatActivity {
    private String stringWeather;
    private TextView textViewMain;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        setInitVIew();
        setData();
        setOnClick();
    }

    private void setInitVIew() {
        textViewMain = findViewById(R.id.java_tv);
        imageView = findViewById(R.id.java_iv);
        button = findViewById(R.id.java_bt);
    }

    private void setData() {
        AppClient.getInstance().getCurrentWeather(new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                Log.i("getWeatherCurrent", "onSuccess t = "+jsonObject);
                if(jsonObject != null && jsonObject.get("cod").getAsString().equals("200")) {
                    Log.i("getWeatherCurrent", "name = "+jsonObject.get("name").getAsString());
                    Log.i("getWeatherCurrent", "main = "+jsonObject.get("main").getAsJsonObject().get("temp").getAsFloat());
                    Log.i("getWeatherCurrent", "main = "+jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString());

                    String cityName = jsonObject.get("name").getAsString();
                    Float temperature = jsonObject.get("main").getAsJsonObject().get("temp").getAsFloat()-273;
                    String description = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
                    stringWeather = "cityName = "+cityName+"\ntemperature = "+temperature+"\ndescription = "+description;
                    textViewMain.setText(stringWeather);

                    String icon = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
                    setImageView("http://openweathermap.org/img/w/"+icon+".png");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("getWeatherCurrent", "onFailure");
                t.printStackTrace();
            }
        });

        AppClient.getInstance().getFiveDaysWeather(new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                Log.i("getFiveDaysWeather", "onSuccess jsonObject = "+jsonObject);
                if(jsonObject != null && jsonObject.get("cod").getAsString().equals("200")) {
                    Log.i("getFiveDaysWeather", "hi");

                    Gson gson = new Gson();
                    Type type = new TypeToken<Map<String, Object>>(){}.getType();
                    Map<String, Object> map = gson.fromJson(jsonObject.toString(), type);

                    getKeyAndValue(map);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("getFiveDaysWeather", "onFailure");
                t.printStackTrace();
            }
        });
    }

    private void setOnClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GpsActivity.class));
            }
        });
    }

    private Handler handler;
    private void setImageView(final String stringUrl) {
        handler= new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(stringUrl);
                    InputStream inputStream = url.openStream();
                    final Bitmap bitmapFactory = new BitmapFactory().decodeStream(inputStream);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmapFactory);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getKeyAndValue(Map<String, Object> map) {
        for (int i = map.size() - 1; i >= 0; i--) {
            Set key = map.keySet();

            for (Iterator iterator = key.iterator(); iterator.hasNext(); ) {
                String keyName = (String) iterator.next();
                String valueName = String.valueOf(map.get(keyName));

                Log.i("getKeyAndValue", "keyName = "+keyName);
                Log.i("getKeyAndValue", "valueName = "+valueName);

//                        ObjectMapper oMapper = new ObjectMapper();
//                        // object -> Map
//                        Map<String, Object> map = oMapper.convertValue(rows.get(i).get(keyName), Map.class);
            }
        }
    }
}
