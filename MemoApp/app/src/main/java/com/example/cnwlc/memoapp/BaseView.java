package com.example.cnwlc.memoapp;

public interface BaseView<T> {
    void setPresenter(T presenter); // presenter 를 setPresenter 를 통해 View 에 전달하는 함수가 기본적으로 추가
}
