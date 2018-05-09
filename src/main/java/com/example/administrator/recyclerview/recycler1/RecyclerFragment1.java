package com.example.administrator.recyclerview.recycler1;

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

public class RecyclerFragment1 extends Fragment {
    /** 생명주기 관련 참고 싸이트 : http://webnautes.tistory.com/1089 */

    @BindView(R.id.RecyclerFragment1RecyclerView)
    RecyclerView recyclerView;

    private List<RecyclerItem1> recyclerItem1List;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter1 recyclerAdapter1;

    private String selectedName = "";

    public static RecyclerFragment1 newInstance() {
        RecyclerFragment1 fragment = new RecyclerFragment1();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler1, container, false);
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

        recyclerItem1List = new ArrayList<>();
        for(int i=0; i<20; i++) {
            RecyclerItem1 recyclerItem1 = new RecyclerItem1("RecyclerFragment1 Test_"+i, false);
            recyclerItem1List.add(recyclerItem1);
        }
        recyclerAdapter1 = new RecyclerAdapter1(recyclerItem1List);
        recyclerView.setAdapter(recyclerAdapter1);
    }

    @OnClick(R.id.RecyclerFragment1Button)
    public void onClickButton(View v) {
        switch (v.getId()) {
            case R.id.RecyclerFragment1Button :
                System.out.println("getName(recyclerAdapter1) : "+getName(recyclerAdapter1));
                break;
        }
    }

    private String getName(RecyclerAdapter1 recyclerAdapter1) {
        selectedName = "";
        List listName = getContactsName(recyclerAdapter1);
        for(int i=0; i<listName.size(); i++) {
            selectedName += listName.get(i)+"\n";
        }

        return selectedName;
    }

    // 이름을 activity 로 리턴
    public List getContactsName(RecyclerAdapter1 recyclerAdapter1) {
        List<RecyclerItem1> list = recyclerAdapter1.getRecyclerItem1List();
        List<String> listName = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            RecyclerItem1 recyclerItem1 = list.get(i);
            if (recyclerItem1.isChecked()) {
                listName.add(recyclerItem1.getName());
            }
        }
        return listName;
    }
}
