package com.example.cnwlc.project_memo;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MemoChangeActivity extends AppCompatActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_GALLERY = 1;
    private static final int PICK_FROM_DRAW = 2;
    private String selectedImagePath;
    final int i = 0;

    TextView Tcontent;
    EditText Econtent;
    String format;
    String Share_contents, Share_title, Suri;
    String Stitle, Scontents, Simportance;

    ImageView Iimg;
    Bitmap Bbit;

    ListItem listitem;
    ArrayList<ListItem> listitemArraylist = new ArrayList<>();
    ListAdapter adapter;

    ImageButton btnAdd, btnCam, btnGal, btnDra, btnCan;

    Animation animSlide_Down, animAdd, animCamera, animDraw, animGallery, animCancel;
    Animation animAddReverse, animCameraReverse, animDrawReverse, animGalleryReverse, animCancelReverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_change);

        setAnimation();

        adapter = new ListAdapter(this, listitemArraylist);

        Tcontent = (TextView) findViewById(R.id.contentsText);
        Econtent = (EditText) findViewById(R.id.contentsEdit);
        Iimg = (ImageView) findViewById(R.id.CimgView);

        Intent intent = getIntent();
        listitem = intent.getParcelableExtra("show_Data");

        Share_title    = listitem.getStitle();
        Share_contents = listitem.getScontent();
        Tcontent.setText(Share_contents);
        Econtent.setText(Share_contents);
        Suri = listitem.getSuri();

        Bbit = BitmapFactory.decodeFile(Suri);
        Iimg.setImageBitmap(Bbit);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd \n HH:mm");
        format = sdf.format(date);

        getSupportActionBar().setTitle("Memo");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFDFBAFF));
    }
    public void setAnimation() {
        btnAdd = (ImageButton) findViewById(R.id.imgAddBtn);
        btnCam = (ImageButton) findViewById(R.id.imgCameraBtn);
        btnDra = (ImageButton) findViewById(R.id.imgDrawBtn);
        btnGal = (ImageButton) findViewById(R.id.imgGalleryBtn);
        btnCan = (ImageButton) findViewById(R.id.imgCancelBtn);

        animSlide_Down = AnimationUtils.loadAnimation(this, R.anim.anim_slide_down);
        animAdd = AnimationUtils.loadAnimation(this, R.anim.anim_add);
        animCamera = AnimationUtils.loadAnimation(this, R.anim.anim_camera);
        animDraw = AnimationUtils.loadAnimation(this, R.anim.anim_draw);
        animGallery = AnimationUtils.loadAnimation(this, R.anim.anim_gallery);
        animCancel = AnimationUtils.loadAnimation(this, R.anim.anim_cancel);

        animAddReverse = AnimationUtils.loadAnimation(this, R.anim.anim_add_reverse);
        animCameraReverse = AnimationUtils.loadAnimation(this, R.anim.anim_camera_reverse);
        animDrawReverse = AnimationUtils.loadAnimation(this, R.anim.anim_draw_reverse);
        animGalleryReverse = AnimationUtils.loadAnimation(this, R.anim.anim_gallery_reverse);
        animCancelReverse = AnimationUtils.loadAnimation(this, R.anim.anim_cancel_reverse);
    }

    //액션바
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_memo_change, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change:
                Tcontent.setVisibility(View.GONE);
                Econtent.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "수정이 완료되면 \"저장\" 버튼을 누르세요.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_store:
                //Dialog에서 보여줄 입력화면 View 객체 생성 작업
                //Layout xml 리소스 파일을 View 객체로 부불려 주는(inflate) LayoutInflater 객체 생성
                LayoutInflater inflater = getLayoutInflater();

                //res폴더>>layout폴더>>dialog_addmember.xml 레이아웃 리소스 파일로 View 객체 생성
                //Dialog의 listener에서 사용하기 위해 final로 참조변수 선언
                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);

                //멤버의 세부내역 입력 Dialog 생성 및 보이기
                AlertDialog.Builder buider = new AlertDialog.Builder(this); //AlertDialog.Builder 객체 생성
                buider.setTitle("제목 입력하기"); //Dialog 제목
                buider.setIcon(android.R.drawable.ic_menu_add); //제목옆의 아이콘 이미지(원하는 이미지 설정)
                buider.setView(dialogView); //위에서 inflater가 만든 dialogView 객체 세팅 (Customize)

                final EditText Etitle = (EditText) dialogView.findViewById(R.id.dialog_edit);
                Etitle.setText(Share_title);
                final Button mSaveBtn = (Button) dialogView.findViewById(R.id.DsaveBtn);
                final CheckBox checkUp = (CheckBox) dialogView.findViewById(R.id.upCheck);
                final CheckBox checkNomal = (CheckBox) dialogView.findViewById(R.id.nomalCheck);
                final CheckBox checkDown = (CheckBox) dialogView.findViewById(R.id.downCheck);

                mSaveBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Etitle 입력된 이름 얻어오기
                        Stitle = Etitle.getText().toString();
                        Scontents = Econtent.getText().toString();

                        if (Scontents.equals("") && Stitle.equals("")) {
                            Toast.makeText(getApplicationContext(), "내용 또는 제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                            return;
                        } else if( !Scontents.equals("") && Stitle.equals("") ) {
                            // 내용이 제목으로 입력된다.
                            Stitle = Scontents;
                            // 체크박스 선택할 경우
                            if ( checkUp.isChecked() == true)         Simportance = checkUp.getText().toString();
                            else if ( checkNomal.isChecked() == true) Simportance = checkNomal.getText().toString();
                            else if ( checkDown.isChecked() == true)  Simportance = checkDown.getText().toString();
                            else Simportance = "하";

                            if(selectedImagePath == null) {
                                selectedImagePath = Suri;
                            }
                            System.out.println(" 2-1 checkUp.isChecked() : " + checkUp.isChecked());
                            System.out.println(" 2-2 !checkUp.isChecked() : " + !checkUp.isChecked());

                            ListItem li = new ListItem(Stitle, Scontents, format, Simportance, selectedImagePath);
                            Intent mi = getIntent();
                            mi.putExtra("All_data_change", li);
                            setResult(RESULT_OK, mi);
                        } else if( !Stitle.equals("") ) {
                            // 체크박스 선택할 경우
                            if ( checkUp.isChecked() == true)         Simportance = checkUp.getText().toString();
                            else if ( checkNomal.isChecked() == true) Simportance = checkNomal.getText().toString();
                            else if ( checkDown.isChecked() == true)  Simportance = checkDown.getText().toString();
                            else Simportance = "하";

                            if(selectedImagePath == null) {
                                selectedImagePath = Suri;
                            }
                            System.out.println(" 3-1 checkUp.isChecked() : " + checkUp.isChecked());
                            System.out.println(" 3-2 !checkUp.isChecked() : " + !checkUp.isChecked());

                            ListItem li = new ListItem(Stitle, Scontents, format, Simportance, selectedImagePath);
                            Intent mi = getIntent();
                            mi.putExtra("All_data_change", li);
                            setResult(RESULT_OK, mi);
                        }
                        finish();
                    }
                });

                //설정한 값으로 AlertDialog 객체 생성
                AlertDialog dialog = buider.create();
                //Dialog의 바깥쪽을 터치했을 때 Dialog를 없앨지 설정
                dialog.setCanceledOnTouchOutside(true);//없어지지 않도록 설정
                //Dialog 보이기
                dialog.show();
                break;
            case R.id.action_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.action_title_contents:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "제목 : " + Share_title);
                intent.putExtra(Intent.EXTRA_TEXT, "내용 : " + Share_contents);

                Intent chooser = Intent.createChooser(intent, "공유");
                startActivity(chooser);
                break;
            case R.id.action_screenshot:
                View container;
                container = getWindow().getDecorView();
                container.buildDrawingCache();
                Bitmap captureView = container.getDrawingCache();
                String address = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Android/data/com.cnwlcjf" + "/capture.jpeg";
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
            case R.id.action_internet:
                Intent intentInternet = new Intent();
                intentInternet.setAction(Intent.ACTION_WEB_SEARCH);
                intentInternet.putExtra(SearchManager.QUERY, "");
                startActivity(intentInternet);
                break;
//            case R.id.action_setting:
//                Toast.makeText(getApplicationContext(), "수정 중입니다.", Toast.LENGTH_SHORT).show();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //액션바 끝

    public void mOnClick(View view) {
        switch (view.getId()) {
            case R.id.imgAddBtn :
                btnAdd.startAnimation(animAdd);
                btnCam.startAnimation(animCamera);
                btnDra.startAnimation(animDraw);
                btnGal.startAnimation(animGallery);
                btnCan.startAnimation(animCancel);

                btnAdd.setVisibility(View.INVISIBLE);
                btnCam.setVisibility(View.VISIBLE);
                btnDra.setVisibility(View.VISIBLE);
                btnGal.setVisibility(View.VISIBLE);
                btnCan.setVisibility(View.VISIBLE);
                break;
            case R.id.imgCancelBtn :
                btnAdd.startAnimation(animAddReverse);
                btnCam.startAnimation(animCameraReverse);
                btnDra.startAnimation(animDrawReverse);
                btnGal.startAnimation(animGalleryReverse);
                btnCan.startAnimation(animCancelReverse);

                btnAdd.setVisibility(View.VISIBLE);
                btnCam.setVisibility(View.GONE);
                btnDra.setVisibility(View.GONE);
                btnGal.setVisibility(View.GONE);
                btnCan.setVisibility(View.GONE);
                break;
            case R.id.imgCameraBtn:
                Intent pictureintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    // 안드로이드 카메라 가이드
                pictureintent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());

                try {
                    pictureintent.putExtra("return-data", true);
                    startActivityForResult(pictureintent, PICK_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
                break;
            case R.id.imgGalleryBtn:
                Intent galleryintent = new Intent();
                // Gallery 호출
                galleryintent.setType("image/*");
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);

                try {
                    galleryintent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(galleryintent, "Complete action using"), PICK_FROM_GALLERY);
                } catch (ActivityNotFoundException e) {
                }
                break;

            case R.id.imgDrawBtn:
                Intent drawIntent = new Intent(this, DrawActivity.class);
                startActivityForResult(drawIntent, PICK_FROM_DRAW);
                break;
        }
    }

    // 사진찍고 저장 및 불러오기, 갤러리에서 사진 불러오기 정의, bit맵 저장
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_FROM_CAMERA) {
                if (data != null) {
                    Bbit = (Bitmap) data.getExtras().get("data");
                    if (Bbit != null) {
                        Iimg.setImageBitmap(Bbit);

                        Uri cameraUri = data.getData();
                        selectedImagePath = getPath(cameraUri);
                    }
                }
            }
            if (requestCode == PICK_FROM_GALLERY) {
                Uri galleryUri = data.getData();
                selectedImagePath = getPath(galleryUri);

                Bbit = BitmapFactory.decodeFile(selectedImagePath);
                Iimg.setImageBitmap(Bbit);
            }
            if (requestCode == PICK_FROM_DRAW) {
                selectedImagePath = data.getStringExtra("saveUri");

                Bbit = BitmapFactory.decodeFile(selectedImagePath);
                Iimg.setImageBitmap(Bbit);
            }
        }
    }

    public String getPath(Uri uri) {
        // uri가 null일경우 null반환
        if (uri == null) {
            return null;
        }
        // 미디어스토어에서 유저가 선택한 사진의 URI를 받아온다.
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // URI경로를 반환한다.
        return uri.getPath();
    }
}
