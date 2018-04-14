package com.example.administrator.test_app;

import android.support.v4.app.Fragment;

public class SampleFragment extends Fragment implements Contract.View {

    private Contract.Presenter mPresenter;

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
//        mPresenter = CheckNotNull(presenter);
    }
}
