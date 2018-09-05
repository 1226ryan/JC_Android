package com.unisight.unisight;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

// 구글 스프레드시트를 DB로 사용하도록 전체 수정할 예정
// 참고 http://itmir.tistory.com/598?category=703047
// 사용할 시트 https://docs.google.com/spreadsheets/d/1SDNV3Bn7Rw3Fk4mv7jc8-5j7hZlGs2laxr7-LZHou4Q/edit#gid=0
public class AppsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<DynamicListViewItem> data;
    private DynamicListViewAdapter adapter;
    private int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.recommend_apps);
        }

        Intent intent = getIntent();
        category = intent.getIntExtra(MainActivity.EXTRA_NAME_RECOMMEND_APP_CATEGORY, MainActivity.EXTRA_VALUE_RECOMMEND_APP_ERROR);

        data = new ArrayList<>();
        switch (category) {
            case MainActivity.EXTRA_VALUE_RECOMMEND_APP_ERROR:
                finish();
                break;
            case MainActivity.EXTRA_VALUE_RECOMMEND_APP_USEFUL:
                data.add(new DynamicListViewItem("자원봉사자가 장애인의 눈이 되어주는 \"Be my eyes\"", "화상채팅으로 시각장애인의 폰 뒷면 카메라로 화면을 비춰주고 질문을 할 수 있습니다."));
                data.add(new DynamicListViewItem("지폐 감별앱 \"MCT Money Reader\"", "폰 뒷면 카메라를 통해 어떤 지폐인지 알려줍니다."));
                data.add(new DynamicListViewItem("사물 인식앱 \"TapTapSee\"", "폰 뒷면 카메라를 통해 사물의 명칭, 색상 등을 알려줍니다."));
                data.add(new DynamicListViewItem("점자 교육앱 \"봄\"", "시각장애인이 점자를 쉽게 배울 수 있는 교육어플 입니다."));
                break;
            case MainActivity.EXTRA_VALUE_RECOMMEND_APP_ENTERTAINMENT:
                data.add(new DynamicListViewItem("재밌는 음성 방송 \"팟캐스트\"", ""));
                data.add(new DynamicListViewItem("시각장애인 전용 포털 \"실로암 포네\"", "시각장애인간 정보, 도서관, 커뮤니티 공간을 제공하는 포털 앱."));
                data.add(new DynamicListViewItem("음성 도서관 \"행복을 들려주는 도서관\"", "음성으로 책을 들을 수 있는 행복을 들려주는 도서관."));
                data.add(new DynamicListViewItem("음성 도서관 \"모바일 소리책\"", "원하는 책을 들을 수 있는 모바일 소리책."));
                break;
            case MainActivity.EXTRA_VALUE_RECOMMEND_APP_WEATHER:
                data.add(new DynamicListViewItem("미세먼지 정보 \"미세미세\"", "오늘의 미세먼지를 확인 할 수 있는 미세미세."));
                data.add(new DynamicListViewItem("날씨정보 \"케이웨더 날씨\"", "오늘의 날씨를 확인할 수 있는 웨더뉴스."));
                data.add(new DynamicListViewItem("카톡으로 받아보는 날씨정보 \"웨더뉴스 카톡\"", "매일 아침 전국의 기온과 날씨를 카톡으로 알려줍니다. 친구추가를 하시면 됩니다."));
                break;
        }
        adapter = new DynamicListViewAdapter(this, data);

        ListView contentsListView = findViewById(R.id.listview_apps);
        contentsListView.setAdapter(adapter);
        contentsListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] category_useful = {"com.bemyeyes.bemyeyes", "com.mctdata.ParaTanima", "com.msearcher.taptapsee.android", "com.project.why.braillelearning"};

        String[] category_entertainment = {"com.freeapp.androidapp", "com.neoacc.siloarm", "com.cbs.happylib", "kr.or.bis"};

        String[] category_weather = {"cheehoon.ha.particulateforecaster", "kr.co.kweather"};

        Intent intent;
        switch (category) {
            case MainActivity.EXTRA_VALUE_RECOMMEND_APP_USEFUL:
                intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(category_useful[position]);
                if (intent != null) {
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(intent);
                    return;
                }
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + category_useful[position])));
                break;
            case MainActivity.EXTRA_VALUE_RECOMMEND_APP_ENTERTAINMENT:
                intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(category_entertainment[position]);
                if (intent != null) {
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(intent);
                    return;
                }
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + category_entertainment[position])));
                break;
            case MainActivity.EXTRA_VALUE_RECOMMEND_APP_WEATHER:
                intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(category_weather[position]);
                if (intent != null) {
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(intent);
                    return;
                }
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + category_weather[position])));
                break;
        }
    }
}