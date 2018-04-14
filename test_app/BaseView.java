package com.example.administrator.test_app;

public interface BaseView<T> {
    // Activity 에서 생성시점이 발생할 수 있기 때문에 설정
    void setPresenter(T presenter);

    // 네트워크 처리가 발생하였을 경우 항상 호출될 수 있는 부분으로 전역으로 적용하였습니다.
    void showProgress();
    void hideProgress();
}
/**
 *  구글의 샘플은 Activity 에서 Prenester를 생성하고, 이를 Fregment(View)에서 전달받아 동일한 Presenter를 양쪽에서 가지고 있도록 만들었음.
 *
 */