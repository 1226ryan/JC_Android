package com.example.cnwlc.project_memo;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MemoMainActivity extends AppCompatActivity {
    final int REQUEST_MEMO = 1;
    final int REQUEST_CHANGE = 2;

    ArrayList<ListItem> listitem = new ArrayList<>();
    ListAdapter adapter;
    ListView listView;

    SharedPreferences pref = null;
    SharedPreferences.Editor edit;

    String format;
    String[] ObjTitle;
    String[] ObjContent;
    String[] ObjFormat;
    String[] ObjImport;
    String[] ObjPath;

    JSONObject jObject;
    JSONArray jArray;
    JSONObject dataObject;

    int listcount;
    int savingListcount;
    int position_0 = 0;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_main);

        // 리스트뷰 관련 정의
        setListView();

        // 날짜 정의
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd \n HH:mm");
        format = sdf.format(date);

        // 접속한 아이디 받아오기
        Intent receiveIdIntent = getIntent();
        String receiveStrId = receiveIdIntent.getStringExtra("New_id");

        // sharedpreferences로 처음 켜질 경우 저장 불러오기
        // 접속한 아이디 마다 새로운 pref 구성 -> 각자 메모장 구성
        pref = getSharedPreferences("save" + receiveStrId, MODE_PRIVATE);
        edit = pref.edit();
        savingListcount = pref.getInt("savelistcount", 0);

        // 저장한 제목/내용/날짜/중요도/경로 받아올 string 배열 정의 및 입력
        String[] saveTitle = new String[savingListcount];
        String[] saveContent = new String[savingListcount];
        String[] saveFormat = new String[savingListcount];
        String[] saveImport = new String[savingListcount];
        String[] savePath = new String[savingListcount];
        for (int i = 0; i < savingListcount; i++) {
            saveTitle[i] = pref.getString("savetitle_" + i, "title_" + i);
            saveContent[i] = pref.getString("savecontent_" + i, "content_" + i);
            saveFormat[i] = pref.getString("saveformat_" + i, "" + format);
            saveImport[i] = pref.getString("saveimport_" + i, "import_" + i);
            savePath[i] = pref.getString("savepath_" + i, "path_" + i);
        }

        // string으로 받아온 제목/내용... 들 ListItem 화 시켜 listiem.add로 넣어줌
        for (int i = 0; i < savingListcount; i++) {
            ListItem lt = new ListItem(saveTitle[i], saveContent[i], saveFormat[i], saveImport[i], savePath[i]);
            listitem.add(0, lt);
            adapter.notifyDataSetChanged();
        }

        // 액션바 정의
        getSupportActionBar().setTitle("Memo");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFDFBAFF));
    }

    // 리스트뷰 정의 및 어댑터에 리스트 뷰 아이템 담고 리스튜트에 셋 시키기.
    void setListView() {
        listView = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter(this, listitem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                Animation anicha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_list_change);
                view.startAnimation(anicha);

                mHandler = new Handler();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Intent intent = new Intent(MemoMainActivity.this, MemoChangeActivity.class);
                                    intent.putExtra("show_Data", listitem.get(position));
                                    intent.putExtra("position", position);
                                    startActivityForResult(intent, REQUEST_CHANGE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 500);
                    }
                });
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                Animation anideldia = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_list_delete_dialog);
                view.startAnimation(anideldia);

                AlertDialog.Builder dlg = new AlertDialog.Builder(MemoMainActivity.this);
                dlg.setTitle(" 삭제 ")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                edit.remove("savetitle_" + position);
                                edit.remove("savecontent_" + position);
                                edit.remove("saveformat_" + position);
                                edit.remove("saveimport_" + position);
                                edit.remove("savepath_" + position);

                                Animation anidel = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_list_delete);
                                view.startAnimation(anidel);

                                listitem.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Animation anican = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_list_delete_cancel);
                                view.startAnimation(anican);
                            }
                        });
                AlertDialog al = dlg.create();
                al.setCanceledOnTouchOutside(false);
                al.show();
                return true;
            }
        });
    }

    //액션바
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_memo:
                Intent intentToMemoI = new Intent(this, MemoInputActivity.class);
                intentToMemoI.putExtra("position_0", 0);
                startActivityForResult(intentToMemoI, REQUEST_MEMO);
                break;
            case R.id.action_delete_all:
                AlertDialog.Builder dlg = new AlertDialog.Builder(MemoMainActivity.this);
                dlg.setTitle("전체삭제")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("메모 전체를 삭제하시겠습니까?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listitem.clear();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                break;
            case R.id.action_Dday:
                Intent alarmIntent = new Intent(this, AlarmMain.class);
                startActivity(alarmIntent);
                break;
            case R.id.action_sort_name:
                adapter.setNameAsc();
                break;
            case R.id.action_sort_impo:
                adapter.setImportAsc();
                break;
            case R.id.action_screenshot:
                View container;
                container = getWindow().getDecorView();
                container.buildDrawingCache();
                Bitmap captureView = container.getDrawingCache();
                String address = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.cnwlcjf" + "/capture.jpeg";
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(address);
                    captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Uri uri = Uri.fromFile(new File(address));
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "공유"));
                break;
            case R.id.action_naver:
                Uri uri_naver = Uri.parse("http://www.naver.com");
                Intent it = new Intent(Intent.ACTION_VIEW, uri_naver);
                startActivity(it);
                break;
            case R.id.action_google:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "");
                startActivity(intent);
                break;
            case R.id.action_timer_countdown:
                Intent TimerIntent = new Intent(MemoMainActivity.this, Timer_CountDown.class);
                startActivity(TimerIntent);
                break;
            case R.id.action_game:
                Intent Game_1to50_intent = new Intent(MemoMainActivity.this, GameActivity.class);
                startActivity(Game_1to50_intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //액션바 끝


    // 아이템 클릭 리스너에서 넘어간 값에 수정 및 저장된 값을 받아와 리스트뷰에 set 및 add 시킴
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MEMO && resultCode == RESULT_OK) {
            ListItem LM = data.getParcelableExtra("All_data");

            if (!LM.equals("")) {
                listitem.add(0, LM);
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == REQUEST_CHANGE && resultCode == RESULT_OK) {
            int countData = data.getIntExtra("position", 0);
            ListItem LM_C = data.getParcelableExtra("All_data_change");

            if (!LM_C.equals("")) {
                listitem.set(countData, LM_C);
                adapter.notifyDataSetChanged();
            }
        }
    }

    // SharedPreference & JSONObject/Array 사용해 데이터 저장
    @Override
    protected void onPause() {
        super.onPause();
        listcount = listView.getCount();
        System.out.println("listcount : " + listcount);

        jObject = new JSONObject();
        jArray = new JSONArray();

        for (int i = 0; i < listcount; i++) {
            JSONObject inObject = new JSONObject();
            try {
                inObject.put("title" + i, listitem.get(listcount - 1 - i).getStitle());
                inObject.put("content" + i, listitem.get(listcount - 1 - i).getScontent());
                inObject.put("format" + i, listitem.get(listcount - 1 - i).getSdaily());
                inObject.put("import" + i, listitem.get(listcount - 1 - i).getSimport());
                inObject.put("path" + i, listitem.get(listcount - 1 - i).getSuri());

                jArray.put(inObject);

                System.out.println("inObject : " + inObject);
                System.out.println("jArray : " + jArray);
            } catch (JSONException e) {
                System.out.println("Data json err : " + e.getMessage());
            }
        }

        try {
            jObject.put("data", jArray);
            System.out.println("jObject : " + jObject);
        } catch (JSONException e) {
            System.out.println("Data json err : " + e.getMessage());
        }

//        JSONObject strObject;
        JSONArray strObjectTOArr;
        ObjTitle = new String[listcount];
        ObjContent = new String[listcount];
        ObjFormat = new String[listcount];
        ObjImport = new String[listcount];
        ObjPath = new String[listcount];
        try {
//            strObject = new JSONObject(jObject.toString());
            strObjectTOArr = jObject.getJSONArray("data");

//            System.out.println("strObject : " + strObject);
            System.out.println("strObjectTOArr : " + strObjectTOArr);
            for (int i = 0; i < strObjectTOArr.length(); i++) {
                dataObject = strObjectTOArr.getJSONObject(i);

                ObjTitle[i] = dataObject.getString("title" + i);
                ObjContent[i] = dataObject.getString("content" + i);
                ObjFormat[i] = dataObject.getString("format" + i);
                ObjImport[i] = dataObject.getString("import" + i);
                ObjPath[i] = dataObject.getString("path" + i);
                System.out.println("dataObject : " + dataObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("What the fuck!!!!!!!!!");
        }

        for (int i = 0; i < listcount; i++) {
            System.out.println("ObjTitle" + i + " : " + ObjTitle[i]);
        }

        for (int i = 0; i < listcount; i++) {
            edit.putString("savetitle_" + i, ObjTitle[i]);
            edit.putString("savecontent_" + i, ObjContent[i]);
            edit.putString("saveformat_" + i, ObjFormat[i]);
            edit.putString("saveimport_" + i, ObjImport[i]);
            edit.putString("savepath_" + i, ObjPath[i]);
        }
        edit.putInt("savelistcount", listcount);
        edit.commit();
    }
}