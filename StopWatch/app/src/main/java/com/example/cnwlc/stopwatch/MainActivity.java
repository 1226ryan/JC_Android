package com.example.cnwlc.stopwatch;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.chronometer_view) Chronometer mChronometer;       // Chronometer는 경과 시간을 측정하는 위젯이다. 즉 스톱워치를 만들수 있다는 뜻인데 0.x초는 안되고 1초단위로만 되기 때문에 약간 불편한 감이 있다.
    @BindView(R.id.start_btn) Button startBtn;
    @BindView(R.id.stop_btn) Button stopBtn;
    @BindView(R.id.reset_btn) Button resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.start_btn, R.id.stop_btn, R.id.reset_btn})
    void click(View v) {
        switch (v.getId()) {
            case R.id.start_btn :
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                mChronometer.setBase(SystemClock.elapsedRealtime());    // 부팅 후의 경과 시간을 전달, 보통 elapsedRealtime 메소드로 경과 시간을 전달
                mChronometer.start();   // start 메소드는 시간 갱신을 시작
                break;
            case R.id.stop_btn :
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                mChronometer.stop();    // stop 메소드는 시간 갱신을 중지
                break;
            case R.id.reset_btn :
                Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                mChronometer.stop();
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mChronometer.start();   // start 메소드는 시간 갱신을 시작
                break;
        }
    }

    public void onDestroy(){
        super.onDestroy();
        mChronometer.stop();        // 그리고 앱이 종료되기전에 반드시 stop을 해줘야 메모리leak이 발생하지 않는다.
    }
}
