package com.example.administrator.test_recyclerview;

//import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

//import com.example.administrator.test_recyclerview.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String[] names = {"Charlie", "Andrew", "Han", "Liz", "Thomas", "Sky", "Andy", "Lee", "Park"};
    private int[] resources = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground};
//    private ActivityMainBinding mainBinding;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLo;

    private ArrayList<RecyclerItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLo = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLo);

//        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setRecyclerView();
        setRefresh();
    }

    private void setRecyclerView() {
        // 각 Item 들이 RecyclerView 의 전체 크기를 변경하지 않는 다면 setHasFixedSize() 함수를 사용해서 성능을 개선할 수 있습니다.
        // 변경될 가능성이 있다면 false 로 , 없다면 true를 설정해주세요.
//        mainBinding.recyclerView.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);

        // RecyclerView 에 Adapter 를 설정해줍니다.
        adapter = new RecyclerAdapter(mItems);

//        mainBinding.recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(adapter);

        // 다양한 LayoutManager 가 있습니다. 원하시는 방법을 선택해주세요.
        // 지그재그형의 그리드 형식
        //mainBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        // 그리드 형식
//        mainBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // 가로 또는 세로 스크롤 목록 형식
//        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 아이템 사이에 구분선
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
//        mainBinding.recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.addItemDecoration(dividerItemDecoration);
        //mainBinding.recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));

        // 아이템 클릭
//        mainBinding.recyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(getApplicationContext(), mainBinding.recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Toast.makeText(getApplicationContext(), position + "번 째 아이템 클릭", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                        Toast.makeText(getApplicationContext(), position + "번 째 아이템 롱 클릭", Toast.LENGTH_SHORT).show();
//                    }
//                }));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), position + "번 째 아이템 클릭", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), position + "번 째 아이템 롱 클릭", Toast.LENGTH_SHORT).show();
                    }
                }));

        setData();
    }

    private void setRefresh() {
//        mainBinding.swipeRefreshLo.setOnRefreshListener(this);
//        mainBinding.swipeRefreshLo.setColorSchemeColors(getResources().getIntArray(R.array.google_colors));
        swipeRefreshLo.setOnRefreshListener(this);
        swipeRefreshLo.setColorSchemeColors(getResources().getIntArray(R.array.google_colors));
    }


    @Override
    public void onRefresh() {
//        mainBinding.recyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Snackbar.make(mainBinding.recyclerView, "Refresh Success", Snackbar.LENGTH_SHORT).show();
//                mainBinding.swipeRefreshLo.setRefreshing(false);
//            }
//        }, 500);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(recyclerView, "Refresh Success", Snackbar.LENGTH_SHORT).show();
                swipeRefreshLo.setRefreshing(false);
            }
        }, 500);
    }

    private void setData() {
        mItems.clear();

        // RecyclerView 에 들어갈 데이터를 추가합니다.
        for (String name : names) {
            for(int resource : resources) {
                mItems.add(new RecyclerItem(name, resource));
            }
        }
        for (String name : names) {
            for(int resource : resources) {
                mItems.add(new RecyclerItem(name, resource));
            }
        }
        for (String name : names) {
            for(int resource : resources) {
                mItems.add(new RecyclerItem(name, resource));
            }
        }

        // 데이터 추가가 완료되었으면 notifyDataSetChanged() 메서드를 호출해 데이터 변경 체크를 실행합니다.
        adapter.notifyDataSetChanged();
    }
}
