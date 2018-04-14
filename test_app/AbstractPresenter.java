package com.example.administrator.test_app;

/**
 * View를 강제하기 위해서 AbstractPresenter를 만들었습니다.
 * 개발상 View에 대한 실수를 줄이기 위해서 만들었는데 괜찮을지 모르겠습니다.(이것도 피드백을 받아서!^^;)
 * @param <V>
 */
public abstract class AbstractPresenter<V extends BaseView> implements BasePresenter {
    private V view;

    public AbstractPresenter(V view) {
        this.view = view;

        view.setPresenter(this);
    }

    public V getView() {
        return view;
    }
}
