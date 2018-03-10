package com.example.administrator.testkeyboard_2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

    private Button m_btns[] = new Button[12]; // 버튼1 ~ 25

    private int m_iStep = 0;
    private int m_ArrNum[] = new int[10];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), button.class);
                startActivity(i);
            }
        });
        // 함수 호출
//        SetBinddingButtons();
    }

//    // 버튼 묶음 정의
//    private void SetBinddingButtons() {
//
//        //버튼 정의
//        for (int i = 0; i < 10; i++) {
//            // m_btns[i] = (Button) findViewById(m_btn_ids[i]);
//            m_btns[i] = (Button) findViewById(R.id.btn_1 + i);
//            Log.d("button", ""+m_btns[i]);
//            m_btns[i].setTextSize(20);
//            m_btns[i].setBackgroundColor(Color.parseColor("#014070"));
//            m_btns[i].setTextColor(Color.parseColor("#ffffff"));
//            m_btns[i].setPadding(1, 1, 1, 1);
//        }
//
//        InitValue();
//    }
//
//    // 초기값정의
//    public void InitValue() {
//        initNumberArr(m_iStep);
//        shakeNumber();
//
//        // m_btns -> 즉, 누를 버튼 정의
//        for (int i = 0; i < 10; i++) {
//                m_btns[i].setText("" + m_ArrNum[i]);
//                m_btns[i].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//        }
//    }
//
//    // 초기값 정의
//    public void initNumberArr(int nStartNum) {
//        for (int i = 0; i < 10; i++) {
//            m_ArrNum[i] = i + nStartNum;
//        }
//    }
//    // 정해진 숫자 섞음
//    public void shakeNumber() {
//    }
}