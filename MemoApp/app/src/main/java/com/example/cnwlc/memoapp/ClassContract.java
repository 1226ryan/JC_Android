package com.example.cnwlc.memoapp;

public interface ClassContract {    // Contract 에는 Presenter와 View를 각각 정의하고 있습니다.
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
