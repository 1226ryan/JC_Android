package com.example.cnwlc.pattern_singleton;

public class SingleTon {
    // 객체가 만들어지면(new SingTon();) heap memory 에 들어감
    // static 변수는 데이터영역의 memory 에 들어감(heap memory 에 들어가는게 아님)
    // SINGLETON_CLASS_INSTANCE << 에는 객체(new SingleTon();)의 주소값이 들어가있음.
    private static SingleTon SINGLETON_CLASS_INSTANCE = new SingleTon();
    public int i = 10;

    private SingleTon() {

    }

    public static SingleTon getSingletonClassInstance() {
        if(SINGLETON_CLASS_INSTANCE == null)
            SINGLETON_CLASS_INSTANCE = new SingleTon();

        return SINGLETON_CLASS_INSTANCE;
    }

    public int getI() {
        return i;
    }
    public void setI(int i) {
        this.i = i;
    }
}
