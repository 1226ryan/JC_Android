package com.example.cnwlc.app_memo.Memo.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cnwlc.app_memo.Common.BaseActivity;
import com.example.cnwlc.app_memo.Memo.Item;
import com.example.cnwlc.app_memo.Memo.RecyclerAdapter;
import com.example.cnwlc.app_memo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mainA_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    private void initView() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(linearLayoutManager);

        List<Item> items = new ArrayList<Item>();
        for(int i=0; i<20; i++) {
            Item item = new Item("어느", "멋진", "날");
            items.add(item);
        }

        recyclerView.setAdapter(new RecyclerAdapter());
    }
}
