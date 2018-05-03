package com.example.administrator.test_permission.dialog_country;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.bitpartner.R;

public class DialogUtilCountry extends AlertDialog {
    private List<String> countrylist;
    private DialogUtilCountryCallBack callBack;

    public static DialogUtilCountry getDialog(@NonNull Context context, List<String> countrylist) {
        DialogUtilCountry dialogUtilCountry = new DialogUtilCountry(context, countrylist);
        return dialogUtilCountry;
    }

    public DialogUtilCountry(@NonNull Context context, List<String> countrylist) {
        super(context);
        this.countrylist = countrylist;
    }

    @BindView(R.id.util_dialog_country_rl)
    RelativeLayout relativeLayout;
    @BindView(R.id.util_dialog_recycler_view) RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<DialogUtilCountryItem> inviteItems = new ArrayList<>();
    private DialogUtilCountryAdapter dialogUtilCountryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.util_dialog_country);
        ButterKnife.bind(this);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setAttributes(layoutParams);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        init();
    }
    private void init() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        for (int i = 0; i < countrylist.size(); i++) {
            DialogUtilCountryItem dialogUtilCountryItem = new DialogUtilCountryItem(countrylist.get(i));
            inviteItems.add(dialogUtilCountryItem);
        }

        dialogUtilCountryAdapter = new DialogUtilCountryAdapter(inviteItems);
        recyclerView.setAdapter(dialogUtilCountryAdapter);
        dialogUtilCountryAdapter.setItemClick(new DialogUtilCountryAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                callBack.onDialogUtilCountryClicked(position);      // 콜백 메서드 안의 position 값을 넣어줌
                dismiss();
            }
        });

        relativeLayout.setOnClickListener(view -> { return; });
    }

    // 아래에서 정의한 콜백을 set할 함수를 만들어줌
    public void setCallBack(DialogUtilCountryCallBack callBack) {
        this.callBack = callBack;

    }

    // 다이얼로그 콜백 메서드를 인터페이스로 정의한 후, position값을 넣어줌
    public interface DialogUtilCountryCallBack {
        void onDialogUtilCountryClicked(int position);
    }
}
