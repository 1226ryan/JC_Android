package com.example.cnwlc.pattern_strategy.robot;

import com.example.cnwlc.pattern_strategy.InterFace.IFly;
import com.example.cnwlc.pattern_strategy.InterFace.IKnife;
import com.example.cnwlc.pattern_strategy.InterFace.IMisail;

public abstract class Robot {
    IFly fly;
    IMisail misail;
    IKnife knife;

    public Robot() {}


    public void actionWalk() {
        System.out.println("걸을 수 있습니다.");
    }
    public void actionRun() {
        System.out.println("달릴 수 있습니다.");
    }
    public abstract void shape();



    public void setFly(IFly fly) {
        this.fly = fly;
    }
    public void setMisail(IMisail misail) {
        this.misail = misail;
    }
    public void setKnife(IKnife knife) {
        this.knife = knife;
    }


    public void actionFly() {
        this.fly.fly();
    }
    public void actionMisail() {
        this.misail.misail();
    }
    public void actionKnife() {
        this.knife.knife();
    }
}
