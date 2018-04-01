package com.example.cnwlc.pattern_singleton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/** Single Ton Pattern
 * 어떤 클래스의 인스턴스는 오직 하나임을 보장, 이 인스턴스에 접근할 수 있는 전역적인 접촉점을 제공하는 패턴
 * 본래 클래스는 여러 개의 객체를 생성하여 사용해왔지만, 이 패턴을 이용하면 클래스 하나에 객체를 유일하게 하나만 생성하여 모든 곳에서 하나의 객체에만 접근할 수 있음.
 * 즉, 전역의 개념으로 객체를 사용할 수 있다.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirstClass firstClass = new FirstClass();
        SecondClass secondClass = new SecondClass();
    }
}
