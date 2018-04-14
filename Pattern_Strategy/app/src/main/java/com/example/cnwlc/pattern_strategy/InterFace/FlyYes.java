package com.example.cnwlc.pattern_strategy.InterFace;

public class FlyYes implements IFly {
    @Override
    public void fly() {
        System.out.println("날 수 있습니다.");
    }
}
