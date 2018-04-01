package com.example.cnwlc.pattern_singleton;

public class SecondClass {
    public SecondClass() {
        SingleTon singleTon = SingleTon.getSingletonClassInstance();
        System.out.println("SecondClass");
        System.out.println("i = "+singleTon.getI());
    }
}
