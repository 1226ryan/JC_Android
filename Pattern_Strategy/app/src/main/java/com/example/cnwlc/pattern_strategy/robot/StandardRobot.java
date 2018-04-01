package com.example.cnwlc.pattern_strategy.robot;

public class StandardRobot extends Robot {
    public StandardRobot() {

    }

    @Override
    public void shape() {
        System.out.println("StandardRobot 입니다. 외형은 팔, 다리, 몸통, 머리가 있습니다.");
    }
}
