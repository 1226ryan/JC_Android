package com.example.cnwlc.pattern_singleton;

public class FirstClass {
    public FirstClass() {
        SingleTon singleTon = SingleTon.getSingletonClassInstance();
        System.out.println("FirstClass");
        System.out.println("i = "+singleTon.getI());
        singleTon.setI(200);
        System.out.println("i = "+singleTon.getI());
    }
}
