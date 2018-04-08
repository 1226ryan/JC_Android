package com.example.cnwlc.retrofit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    double mLatitude;
    double mLongitude;

    @BindView(R.id.tv1)
    TextView textView1;
    @BindView(R.id.tv2)
    TextView textView2;
    @BindView(R.id.tv3)
    TextView textView3;
    @BindView(R.id.tv4)
    TextView textView4;

    String TAG = MainActivity.this.getPackageName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);


    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();  //위도
            //   double altitude = location.getAltitude(); //고도
            //   float accuracy = location.getAccuracy(); //정확도
            //   String provider = location.getProvider(); //위치제공자

            mLatitude = latitude;
            mLongitude = longitude;

            ret();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    WeatherRepo weatherRepo;

    private void ret() {
        Retrofit client = new Retrofit
                .Builder()
                .baseUrl("https://api2.sktelecom.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApiInterface weatherApi = client.create(WeatherApiInterface.class);
        String lat = String.valueOf(mLatitude);
        String lon = String.valueOf(mLongitude);
        System.out.println("latlat : "+lat);
        System.out.println("lonlon : "+lon);

        Call<WeatherRepo> call = weatherApi.get_Weather_retrofit(1, lat, lon);
        call.enqueue(new Callback<WeatherRepo>() {
            @Override
            public void onResponse(Call<WeatherRepo> call, Response<WeatherRepo> response) {
                System.out.println("response : "+response);
                System.out.println("callcall : "+call);
                weatherRepo = response.body();
                System.out.println("weatherRepo : "+weatherRepo);

                if (weatherRepo.getResult().getCode().equals("9200")) {
                    textView1.setText(weatherRepo.getWeather().getHourly().get(0).getHumidity());
                    textView2.setText(weatherRepo.getWeather().getHourly().get(0).getTemperature().getTc());
                    textView3.setText(weatherRepo.getWeather().getHourly().get(0).getSky().getName());
                    textView4.setText(weatherRepo.getWeather().getHourly().get(0).getWind().getWspd());
                } else {
                    Log.d(TAG, "server return error code :" + weatherRepo.getResult().getCode());
                }
            }

            @Override
            public void onFailure(Call<WeatherRepo> call, Throwable t) {
                Log.d(TAG, "onFailure" + t);
            }
        });
    }
}
