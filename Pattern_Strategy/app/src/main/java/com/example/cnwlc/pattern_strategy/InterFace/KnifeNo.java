package com.example.cnwlc.pattern_strategy.InterFace;

public class KnifeNo implements IKnife {
    @Override
    public void knife() {
        System.out.println("검이 없습니다.");
    }
}
