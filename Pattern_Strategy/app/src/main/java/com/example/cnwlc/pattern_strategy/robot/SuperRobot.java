package com.example.cnwlc.pattern_strategy.robot;

import com.example.cnwlc.pattern_strategy.InterFace.FlyYes;
import com.example.cnwlc.pattern_strategy.InterFace.KnifeLaser;
import com.example.cnwlc.pattern_strategy.InterFace.MisailYes;

public class SuperRobot extends Robot {
    public SuperRobot() {
        fly = new FlyYes();
        misail = new MisailYes();
        knife = new KnifeLaser();
    }

    @Override
    public void shape() {
        System.out.println("SuperRobot 입니다. 외형은 팔, 다리, 몸통, 머리가 있습니다.");
    }
}
