package com.example.administrator.recyclerview.recycler2;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerFragment2 extends Fragment {
    /** 생명주기 관련 참고 싸이트 : http://webnautes.tistory.com/1089 */

    @BindView(R.id.RecyclerFragment2RecyclerView)
    RecyclerView recyclerView;

    private List<RecyclerItem2> recyclerItem2List;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter2 recyclerAdapter2;

    private String selectedName = "";

    public static RecyclerFragment2 newInstance() {
        RecyclerFragment2 fragment = new RecyclerFragment2();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler2, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            linearLayoutManager = new LinearLayoutManager(getContext());
        }
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerItem2List = new ArrayList<>();
        for(int i=0; i<30; i++) {
            RecyclerItem2 recyclerItem2 = new RecyclerItem2("RecyclerFragment2 Test_"+i, false);
            recyclerItem2List.add(recyclerItem2);
        }
        recyclerAdapter2 = new RecyclerAdapter2(recyclerItem2List);
        recyclerView.setAdapter(recyclerAdapter2);
    }

    @OnClick(R.id.RecyclerFragment2Button)
    public void onClickButton(View v) {
        switch (v.getId()) {
            case R.id.RecyclerFragment2Button :
                System.out.println("getName(recyclerAdapter2) : "+getName(recyclerAdapter2));
                break;
        }
    }

    private String getName(RecyclerAdapter2 recyclerAdapter2) {
        selectedName = "";
        List listName = getContactsName(recyclerAdapter2);
        for(int i=0; i<listName.size(); i++) {
            selectedName += listName.get(i)+"\n";
        }

        return selectedName;
    }

    // 이름을 activity 로 리턴
    public List getContactsName(RecyclerAdapter2 recyclerAdapter2) {
        List<RecyclerItem2> list = recyclerAdapter2.getRecyclerItem2List();
        List<String> listName = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            RecyclerItem2 recyclerItem2 = list.get(i);
            if (recyclerItem2.isChecked()) {
                listName.add(recyclerItem2.getName());
            }
        }
        return listName;
    }
}
