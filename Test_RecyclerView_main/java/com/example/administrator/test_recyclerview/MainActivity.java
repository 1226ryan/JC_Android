package com.example.administrator.test_recyclerview;

//import android.databinding.DataBindingUtil;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

//import com.example.administrator.test_recyclerview.databinding.ActivityMainBinding;

import com.example.administrator.test_recyclerview.another.RecyclerViewActivity;
import com.example.administrator.test_recyclerview.another2.CardViewActivity;
import com.example.administrator.test_recyclerview.main.RecyclerAdapter;
import com.example.administrator.test_recyclerview.main.RecyclerItem;
import com.example.administrator.test_recyclerview.main.RecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String[] names = {"Charlie", "Andrew", "Han", "Liz", "Thomas", "Sky", "Andy", "Lee", "Park"};
    private int[] resources = {R.drawable.buy_3, R.drawable.kakao, R.drawable.noti_setting};
//    private ActivityMainBinding mainBinding;
    private RecyclerView.Adapter adapter;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLo) SwipeRefreshLayout swipeRefreshLo;

    private ArrayList<RecyclerItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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




    @SuppressLint("MissingPermission")
    @OnClick(R.id.permission)
    public void permission() {
        /* 사용자의 OS 버전이 마시멜로우 이상인지 체크한다. */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /* 사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 체크한다.
             *  int를 쓴 이유? 안드로이드는 C기반이기 때문에, Boolean 이 잘 안쓰인다.
             */
            int permissionResult = checkSelfPermission(Manifest.permission.READ_CONTACTS);

            /* CALL_PHONE의 권한이 없을 때 */
            // 패키지는 안드로이드 어플리케이션의 아이디다.( 어플리케이션 구분자 )
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(MainActivity.this, "통과.", Toast.LENGTH_SHORT).show();
                /* 사용자가 CALL_PHONE 권한을 한번이라도 거부한 적이 있는 지 조사한다.
                 * 거부한 이력이 한번이라도 있다면, true를 리턴한다.
                 */
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    Toast.makeText(MainActivity.this, "사용자가 READ_CONTACTS 권한을 한번이라도 거부한 적이 있는 지 조사.", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("권한이 필요합니다.")
                            .setMessage("이 기능을 사용하기 권한이 필요합니다. 계속하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CAMERA}, 1000);
                                    }
                                }

                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                                }

                            })
                            .create()
                            .show();
                }
                //최초로 권한을 요청할 때
                else {
                    Toast.makeText(MainActivity.this, "READ_CONTACTS 권한을 Android OS 에 요청.", Toast.LENGTH_SHORT).show();
                    // READ_CONTACTS 권한을 Android OS 에 요청한다.
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1000);
                }
            }
            /* READ_CONTACTS 권한이 있을 때 */
            else {
                Toast.makeText(MainActivity.this, "READ_CONTACTS 권한이 있을 때.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                startActivity(intent);
            }
        }
        /* 사용자의 OS 버전이 마시멜로우 이하일 떄 */
        else {
            Toast.makeText(MainActivity.this, "마시멜로이하.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
            startActivity(intent);
        }
    }

    /**
     * 사용자가 권한을 허용했는지 거부했는지 체크
     * @param requestCode   1000번
     * @param permissions   개발자가 요청한 권한들
     * @param grantResults  권한에 대한 응답들
     *                    permissions와 grantResults는 인덱스 별로 매칭된다.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            /* 요청한 권한을 사용자가 "허용"했다면 인텐트를 띄워라
                내가 요청한 게 하나밖에 없기 때문에. 원래 같으면 for문을 돈다.*/
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "권한 요청을 승인했습니다.", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "권한 요청을 승인 및 통과했습니다.", Toast.LENGTH_SHORT).show();
//                    startActivity(intent);
                }
            }
            else {
                Toast.makeText(MainActivity.this, "권한 요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.btn, R.id.another2, R.id.single_selection, R.id.max_selection, R.id.multi_selection})
    public void kotlinBtn(View v) {
        Intent intent = null;
        switch(v.getId()) {
            case R.id.btn :
                intent = new Intent(getApplicationContext(), MainActivity2.class);
                break;
            case R.id.another2 :
                intent = new Intent(MainActivity.this, CardViewActivity.class);
                break;
            case R.id.single_selection :
                intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                intent.putExtra("TAG", "single");
                break;
            case R.id.max_selection :
                intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                intent.putExtra("TAG", "max");
                break;
            case R.id.multi_selection :
                intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                intent.putExtra("TAG", "multiple");
                break;
        }
        startActivity(intent);
    }
}
