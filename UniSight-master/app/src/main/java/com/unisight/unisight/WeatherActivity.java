package com.unisight.unisight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {
    private ArrayList<DynamicListViewItem> data;
    private DynamicListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.weather_detail);
        }

        data = new ArrayList<>();
        adapter = new DynamicListViewAdapter(this, data);

        ListView contentsListView = findViewById(R.id.listview_weather);
        contentsListView.setAdapter(adapter);

        AppClient.getInstance().getWeatherCurrent(new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                if (jsonObject != null) {
                    String city = jsonObject.get("name").getAsString();
                    float temperature = jsonObject.get("main").getAsJsonObject().get("temp").getAsFloat() - 273;
                    String description = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
                    data.add(new DynamicListViewItem(city, String.valueOf((int)temperature) + "도"));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("error", "fail");
            }
        });
    }
}
