package com.example.administrator.test_app;

/**
 * presenter 와 View를 정의 -> BaseView/Presenter 를 상속받아 구현
 */
public interface Contract {
    interface View extends BaseView<Presenter> {
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void saveTask(String title, String description);
    }
}
