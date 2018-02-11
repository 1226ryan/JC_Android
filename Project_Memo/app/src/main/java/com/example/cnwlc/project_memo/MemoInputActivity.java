package com.example.cnwlc.project_memo;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoInputActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_GALLERY = 1;
    private static final int PICK_FROM_DRAW = 2;
    private String selectedImagePath = null;
    static String Stitle = null;
    static String Scontents = null;
    static String Simportance = null;

    ImageView imgview;
    Bitmap photo;

    EditText Econtent;
    String format;

    ImageButton btnAdd;
    ImageButton btnCam;
    ImageButton btnGal;
    ImageButton btnDra;
    ImageButton btnCan;

    Animation animSlide_Down;
    Animation animAdd;
    Animation animCamera;
    Animation animDraw;
    Animation animGallery;
    Animation animCancel;

    Animation animAddReverse;
    Animation animCameraReverse;
    Animation animDrawReverse;
    Animation animGalleryReverse;
    Animation animCancelReverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_input);

        setAnimation();

        Econtent = (EditText) findViewById(R.id.contentEdit);
        imgview = (ImageView) findViewById(R.id.imgView);


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
        inflater.inflate(R.menu.menu_memo_input, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_store:
                Toast.makeText(getApplicationContext(), "체크박스를 2개 이상 선택했을 경우 " +
                        "\n높은 중요도로 정해집니다.", Toast.LENGTH_LONG).show();
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
                final Button mSaveBtn = (Button) dialogView.findViewById(R.id.DsaveBtn);
                final CheckBox checkUp = (CheckBox) dialogView.findViewById(R.id.upCheck);
                final CheckBox checkNomal = (CheckBox) dialogView.findViewById(R.id.nomalCheck);
                final CheckBox checkDown = (CheckBox) dialogView.findViewById(R.id.downCheck);

                mSaveBtn.setOnClickListener(new View.OnClickListener() {
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
                            if ( checkUp.isChecked() == true )         Simportance = checkUp.getText().toString();
                            else if ( checkNomal.isChecked() == true ) Simportance = checkNomal.getText().toString();
                            else if ( checkDown.isChecked() == true )  Simportance = checkDown.getText().toString();
                            else Simportance = "하";

                            if(selectedImagePath == null) {
                                selectedImagePath = "No path";
                            }
                            System.out.println(" 2-1 checkUp.isChecked() : " + checkUp.isChecked());
                            System.out.println(" 2-2 !checkUp.isChecked() : " + !checkUp.isChecked());

                            ListItem li = new ListItem(Stitle, Scontents, format, Simportance, selectedImagePath);
                            Intent mi = getIntent();
                            mi.putExtra("All_data", li);
                            setResult(RESULT_OK, mi);
                        } else if( !Stitle.equals("") ) {
                            // 체크박스 선택할 경우
                            if ( checkUp.isChecked() == true)         Simportance = checkUp.getText().toString();
                            else if ( checkNomal.isChecked() == true) Simportance = checkNomal.getText().toString();
                            else if ( checkDown.isChecked() == true)  Simportance = checkDown.getText().toString();
                            else Simportance = "하";

                            if(selectedImagePath == null) {
                                selectedImagePath = "No path";
                            }
                            System.out.println(" 3-1 checkUp.isChecked() : " + checkUp.isChecked());
                            System.out.println(" 3-2 !checkUp.isChecked() : " + !checkUp.isChecked());

                            ListItem li = new ListItem(Stitle, Scontents, format, Simportance, selectedImagePath);
                            Intent mi = getIntent();
                            mi.putExtra("All_data", li);
                            setResult(RESULT_OK, mi);
                        }
                        finish();
                    }
                });

                //설정한 값으로 AlertDialog 객체 생성
                AlertDialog dialog = buider.create();
                //Dialog의 바깥쪽을 터치했을 때 Dialog를 없앨지 설정
                dialog.setCanceledOnTouchOutside(true);
                //Dialog 보이기
                dialog.show();
                break;
            case R.id.action_cancel:
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("안내");
                ab.setMessage("메모를 저장하지 않고 나가시겠습니까?");
                ab.setIcon(android.R.drawable.ic_dialog_alert);
                ab.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                ab.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dia = ab.create();
                dia.show();
                break;
//            case R.id.action_internet:
//                Uri uri = Uri.parse("http://www.google.com");
//                Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(it);
//                break;
//            case R.id.action_size:
//                Toast.makeText(getApplicationContext(), "수정중입니다.", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.action_color:
//                Toast.makeText(getApplicationContext(), "수정 중입니다.", Toast.LENGTH_SHORT).show();
//                break;
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
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    // 안드로이드 카메라 가이드
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                try {
                    cameraIntent.putExtra("return-data", true);
                    startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
                break;
            case R.id.imgGalleryBtn:
                Intent galleryIntent = new Intent();
                // Gallery 호출
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                try {
                    galleryIntent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(galleryIntent, "Complete action using"), PICK_FROM_GALLERY);
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
                    photo = (Bitmap) data.getExtras().get("data");
                    if (photo != null) {
                        imgview.setImageBitmap(photo);
                        imgview.startAnimation(animSlide_Down);

                        Uri cameraUri = data.getData();
                        selectedImagePath = getPath(cameraUri);
                    }
                }
            }
            if (requestCode == PICK_FROM_GALLERY) {
                Uri galleryUri = data.getData();
                selectedImagePath = getPath(galleryUri);

                photo = BitmapFactory.decodeFile(selectedImagePath);
                imgview.setImageBitmap(photo);
                imgview.startAnimation(animSlide_Down);
            }
            if (requestCode == PICK_FROM_DRAW) {
                selectedImagePath = data.getStringExtra("saveUri");

                photo = BitmapFactory.decodeFile(selectedImagePath);
                imgview.setImageBitmap(photo);
                imgview.startAnimation(animSlide_Down);
            }
        }
    }

    /**
     * 사진의 URI 경로를 받는 메소드
     */
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

    float oldXvalue;
    float oldYvalue;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int width = ((ViewGroup) v.getParent()).getWidth() - v.getWidth();
        int height = ((ViewGroup) v.getParent()).getHeight() - v.getHeight();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oldXvalue = event.getX();
            oldYvalue = event.getY();
            //  Log.i("Tag1", "Action Down X" + event.getX() + "," + event.getY());
            Log.i("Tag1", "Action Down rX " + event.getRawX() + "," + event.getRawY());
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            v.setX(event.getRawX() - oldXvalue);
            v.setY(event.getRawY() - (oldYvalue + v.getHeight()));
            //  Log.i("Tag2", "Action Down " + me.getRawX() + "," + me.getRawY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (v.getX() > width && v.getY() > height) {
                v.setX(width);
                v.setY(height);
            } else if (v.getX() < 0 && v.getY() > height) {
                v.setX(0);
                v.setY(height);
            } else if (v.getX() > width && v.getY() < 0) {
                v.setX(width);
                v.setY(0);
            } else if (v.getX() < 0 && v.getY() < 0) {
                v.setX(0);
                v.setY(0);
            } else if (v.getX() < 0 || v.getX() > width) {
                if (v.getX() < 0) {
                    v.setX(0);
                    v.setY(event.getRawY() - oldYvalue - v.getHeight());
                } else {
                    v.setX(width);
                    v.setY(event.getRawY() - oldYvalue - v.getHeight());
                }
            } else if (v.getY() < 0 || v.getY() > height) {
                if (v.getY() < 0) {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(0);
                } else {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(height);
                }
            }
        }
        return true;
    }
}