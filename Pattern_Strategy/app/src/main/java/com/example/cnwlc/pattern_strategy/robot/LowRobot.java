package com.example.cnwlc.pattern_strategy.robot;

public class LowRobot extends Robot {
    public LowRobot() {

    }

    @Override
    public void shape() {
        System.out.println("LowRobot 입니다. 외형은 팔, 다리, 몸통, 머리가 있습니다.");
    }
}
