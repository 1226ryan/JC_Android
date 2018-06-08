package com.example.choo.project_study.my_information.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choo.project_study.BaseActivity;
import com.example.choo.project_study.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.my_information_image_view_picture)
    ImageView imageViewMyPicture;
    @BindView(R.id.my_information_text_view_name)
    TextView textViewName;
    @BindView(R.id.my_information_text_view_follow)
    TextView textViewFollow;
    @BindView(R.id.my_information_text_view_following)
    TextView textViewFollowing;

    @BindView(R.id.my_information_recycler_view)
    RecyclerView recyclerVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.my_information_image_view_picture, R.id.my_information_text_view_follow, R.id.my_information_text_view_following,
            R.id.my_information_button_modify, R.id.my_information_relative_layout_event, R.id.my_information_relative_layout_setting})
    public void onClickEvent(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.my_information_image_view_picture :
                break;
            case R.id.my_information_text_view_follow :
                intent = new Intent(MainActivity.this, BaseActivity.class);
                break;
            case R.id.my_information_text_view_following :
                intent = new Intent(MainActivity.this, BaseActivity.class);
                break;
            case R.id.my_information_button_modify :
                intent = new Intent(MainActivity.this, BaseActivity.class);
                break;
            case R.id.my_information_relative_layout_event :
                intent = new Intent(MainActivity.this, BaseActivity.class);
                break;
            case R.id.my_information_relative_layout_setting :
                intent = new Intent(MainActivity.this, BaseActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }
}
