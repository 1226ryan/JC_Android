package com.unisight.unisight;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class NewsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private boolean firstLoadNews;
    private ArrayList<DynamicListViewItem> data;
    private DynamicListViewAdapter adapter;
    private HashMap<String, String> newsLinkMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.highlight_news);
        }

        firstLoadNews = true;
        data = new ArrayList<>();
        adapter = new DynamicListViewAdapter(this, data);

        ListView contentsListView = findViewById(R.id.listview_news);
        contentsListView.setAdapter(adapter);
        contentsListView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (firstLoadNews) {
            refreshNews();
            firstLoadNews = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu_news, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshNews();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int count = 0;
        for(String key : newsLinkMap.keySet()) {
            if (count == position) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsLinkMap.get(key)));
                startActivity(intent);
                break;
            }
            count++;
        }
    }

    private void refreshNews() {
        data.clear();

        NewsParser newsTask = new NewsParser();

        try {
            newsLinkMap = newsTask.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (newsLinkMap != null) {
            for (String newsTitle : newsLinkMap.keySet()) {
                data.add(new DynamicListViewItem(newsTitle, ""));
            }
            adapter.notifyDataSetChanged();
        }
    }
}
