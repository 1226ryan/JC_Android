package com.example.cnwlc.test_paging;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * http://sharp57dev.tistory.com/9 참고
 */
public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

//    private ListView listView;                      // 리스트뷰
//    private List<String> list;                      // String 데이터를 담고있는 리스트
//    private ListViewAdapter adapter;                // 리스트뷰의 아답터

    ArrayList<ListViewItem> arrayList = new ArrayList<>();      // ListViewItem 데이터를 담고 있는 어레이리스트
    ListViewAdapter adapter;        // 리스트뷰 어댑터
    ListView listView;              // 리스트뷰
    ListViewItem li;                // 리스트 아이템에 대한 값을 담을 리스트아이템
    String format;                  // 날짜를 담을 변수
    String Simportance = "중";

    private int page = 0;                           // 페이징변수. 초기 값은 0 이다.
    private final int OFFSET = 15;                  // 한 페이지마다 로드할 데이터 갯수.

    private ProgressBar progressBar;                // 데이터 로딩중을 표시할 프로그레스바
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수

    String[] label_ba = new String[50];
    int label_length = label_ba.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        variable();
        getItem();
    }

    void variable() {
//        listView = (ListView) findViewById(R.id.listview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//
//        list = new ArrayList<String>();
//        adapter = new ListViewAdapter(this, list);
//        listView.setAdapter(adapter);
//
        progressBar.setVisibility(View.GONE);
//
//        listView.setOnScrollListener(this);


        listView = (ListView) findViewById(R.id.listview);
        adapter = new ListViewAdapter(this, arrayList);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);

        // 날짜 정의
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd \n HH:mm");
        format = sdf.format(date);

        for (int i = 0; i < 50; i++) {
            label_ba[i] = "Label " + (i+1);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "item : " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 스크롤 함수
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 1. OnScrollListener.SCROLL_STATE_IDLE : 스크롤이 이동하지 않을때의 이벤트(즉 스크롤이 멈추었을때).
        // 2. lastItemVisibleFlag : 리스트뷰의 마지막 셀의 끝에 스크롤이 이동했을때.
        // 3. mLockListView == false : 데이터 리스트에 다음 데이터를 불러오는 작업이 끝났을때.
        // 1, 2, 3 모두가 true일때 다음 데이터를 불러온다.
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            if(label_length > 0 ) {
                System.out.println("scrollState : "+scrollState);
                System.out.println("lastItemVisibleFlag : "+lastItemVisibleFlag);
                System.out.println("mLockListView : "+mLockListView);

                // 화면이 바닦에 닿을때 처리
                // 로딩중을 알리는 프로그레스바를 보인다.
                progressBar.setVisibility(View.VISIBLE);

                // 다음 데이터를 불러온다.
                getItem();
            }
        }
    }
    // 스크롤 함수2
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // firstVisibleItem : 화면에 보이는 첫번째 리스트의 아이템 번호.
        // visibleItemCount : 화면에 보이는 리스트 아이템의 갯수
        // totalItemCount : 리스트 전체의 총 갯수
        // 리스트의 갯수가 0개 이상이고, 화면에 보이는 맨 하단까지의 아이템 갯수가 총 갯수보다 크거나 같을때.. 즉 리스트의 끝일때. true
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }

    // 첫 시작과 스크롤 시 받아올 함수
    private void getItem() {
        mLockListView = true;

        System.out.println("label_length : " + label_length);
        System.out.println("page : " + page);

        if (label_length > 15) {
            for (int i = label_length - 1; i >= label_length - OFFSET; i--) {
                li = new ListViewItem(label_ba[i], format, Simportance);
//            String label = "Label " + ((page * OFFSET) + i);
//                list.add(label_ba[i]);
                arrayList.add(li);
            }
            label_length = label_length - 15;
        } else {
            for (int i = label_length-1; i >= 0; i--) {
                li = new ListViewItem(label_ba[i], format, Simportance);
//            String label = "Label " + ((page * OFFSET) + i);
//                list.add(label_ba[i]);
                arrayList.add(li);
            }
            label_length = 0;
        }
        System.out.println("label_ba[" + label_length + "] : " + label_ba[label_length]);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                mLockListView = false;
            }
        }, 1000);


//        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 mLockListView 를 true로 설정한다.
//        mLockListView = true;
//        // 다음 20개의 데이터를 불러와서 리스트에 저장한다.
//        for(int i = 0; i < 20; i++){
//            String label = "Label " + ((page * OFFSET) + i);
//            list.add(0, label);
//        }
//        // 1초 뒤 프로그레스바를 감추고 데이터를 갱신하고, 중복 로딩 체크하는 Lock을 했던 mLockListView변수를 풀어준다.
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                page++;
//                adapter.notifyDataSetChanged();
//                progressBar.setVisibility(View.GONE);
//                mLockListView = false;
//            }
//        },1000);
    }
}