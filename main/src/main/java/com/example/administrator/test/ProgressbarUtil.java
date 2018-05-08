package com.example.administrator.test;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2018-03-23.
 */

public class ProgressbarUtil {

    private ProgressBar progressBar;
    private static ProgressbarUtil instance;

    private ProgressbarUtil() {}
    public static ProgressbarUtil getInstance() {
        if (instance == null)
            instance = new ProgressbarUtil();

        return instance;
    }

    /** ViewGroup layout : layout의 젤 상위 layout 을 가져오면 됨 */
    public void showProgressbar(Context context, ViewGroup layout) {
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(150, 150);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        layout.addView(progressBar, params);
    }
    public void hideProgressbar(ViewGroup layout) {
        layout.removeView(progressBar);
    }

}
