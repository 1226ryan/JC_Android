package com.example.cnwlc.misemunjiapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView dataTime;
    TextView dataTime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTime = (TextView) findViewById(R.id.textView1);
        dataTime2 = (TextView) findViewById(R.id.textView2);

        new MyThread().execute();
    }

    class MyThread extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            Air air = AirService.getAir();
            Log.d("air", air.toString());
            return air;
        }

        //주체 : mainThread
        @Override
        protected void onPostExecute(Object o) {
            Log.d("air", "onPostExecute:" + o.toString());
            Air air = (Air) o;
            dataTime.setText(air.getDataTime());
            dataTime2.setText(air.seoul);
        }
    }
}
