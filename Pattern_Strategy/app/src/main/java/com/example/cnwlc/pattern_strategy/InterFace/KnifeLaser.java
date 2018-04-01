package com.example.cnwlc.pattern_strategy.InterFace;

public class KnifeLaser implements IKnife {
    @Override
    public void knife() {
        System.out.println("레이저 검이 있습니다.");
    }
}
