package com.example.cnwlc.pattern_strategy.robot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cnwlc.pattern_strategy.InterFace.FlyYes;
import com.example.cnwlc.pattern_strategy.InterFace.KnifeLaser;
import com.example.cnwlc.pattern_strategy.InterFace.MisailYes;
import com.example.cnwlc.pattern_strategy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        superRobot();
    }
    private void superRobot() {
        System.out.println("SuperRobot을 만들어 주세요.");

        Robot superRobot = new SuperRobot();
        superRobot.shape();
        superRobot.actionRun();
        superRobot.actionWalk();

//        superRobot.setFly(new FlyYes());
//        superRobot.setMisail(new MisailYes());
//        superRobot.setKnife(new KnifeLaser());
        // set 을 잊어먹거나 하기 귀찮으면 SuperRobot 안에 디폴트 값을 주고 변경하고 싶으면 위 방식처럼 new FlyNo() 로 바꿔주면 된다.
        // default 값을 안줄거면 위처럼 그냥 할당해주면 됨.

        superRobot.actionFly();
        superRobot.actionMisail();
        superRobot.actionKnife();
    }
}
