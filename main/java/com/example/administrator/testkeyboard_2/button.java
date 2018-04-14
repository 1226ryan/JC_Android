package com.example.administrator.testkeyboard_2;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-03-08.
 */

public class button extends AppCompatActivity implements View.OnClickListener {

    private Button m_btns[] = new Button[10];
    private int m_ArrNum[] = new int[10];

    boolean customKeyboard = false;
    int length;
    String str;
    EditText editText;
    TextView textView;
    ImageButton deleteBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to_fifty);

        setVariable();
        SetBinddingButtons();
    }

    void setVariable() {
        textView = (TextView) findViewById(R.id.tv);
        editText = (EditText) findViewById(R.id.et);
        deleteBtn = (ImageButton) findViewById(R.id.btn_9);

        // 입출한도알아보기 버튼
        final View max = findViewById(R.id.custom);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customKeyboard = !customKeyboard;
                if(customKeyboard == true) {
                    max.setVisibility(View.VISIBLE);
                } else {
                    max.setVisibility(View.GONE);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("delete length : "+length);
                textView.setText(textView.getText().toString().substring(0, length-1));
                length = length-1;
            }
        });
    }

    // 버튼 묶음 정의
    private void SetBinddingButtons() {
        //버튼 정의
        for (int i = 0; i < 10; i++) {
            m_btns[i] = (Button) findViewById(R.id.btn_1 + i);
            System.out.println("m_btns["+i+"] : "+m_btns[i]);
            m_btns[i].setOnClickListener(this);
            m_btns[i].setTextSize(20);
            m_btns[i].setBackgroundColor(Color.parseColor("#014070"));
            m_btns[i].setTextColor(Color.parseColor("#ffffff"));
            m_btns[i].setPadding(1, 1, 1, 1);
        }

        // 1 ~ 25버튼 생성 및 x, y좌표로 숫자 섞어주기 - 함수 호출
        initNumberArr();
        shakeNumber();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int s = i*3 +j;
                if(s < 10) {
                    m_btns[s].setText("" + m_ArrNum[s]);
                    m_btns[s].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                } else {
                    continue;
                }
            }
        }
    }

    // 클릭시 이벤트 발생
    public void onClick(View v) {
        str = ((Button)v).getText().toString();
        editText.append(str);
        textView.append(str);
        length = textView.length();
        System.out.println("onClick length : "+length);
    }

    // 초기값 정의
    public void initNumberArr() {
        for (int i = 0; i < 10; i++) {
            m_ArrNum[i] = i;
        }
    }

    // 정해진 숫자 섞음
    public void shakeNumber() {
        int x = 0;
        int y = 0;
        int tmp = 0;
        Random _ran = new Random();

        for (int i = 0; i < 100; i++) {
            x = _ran.nextInt(10);
            y = _ran.nextInt(10);

            if (x == y) continue;

            tmp = m_ArrNum[x];
            m_ArrNum[x] = m_ArrNum[y];
            m_ArrNum[y] = tmp;
        }
    }
}