package com.example.administrator.test_app;

/**
 * Presenter는 하나의 View만을 사용하게 될 것이라 생각하고 설계....
 */
public class ViewExamplePresenter extends AbstractPresenter<ViewExampleContract.View> implements ViewExampleContract.Presenter {
    public ViewExamplePresenter(ViewExampleContract.View view) {
        super(view);
    }

    @Override
    public void start() {

    }
}
