package com.example.cnwlc.pattern_strategy.InterFace;

public class MisailNo implements IMisail {
    @Override
    public void misail() {
        System.out.println("미사일을 쏠 수 없습니다.");
    }
}
