package com.example.administrator.test_recyclerview.invitation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import co.bitpartner.R;
import co.bitpartner.app.BaseActivity;
import co.bitpartner.app.fund.FundActivity;
import co.bitpartner.util.DialogUtil;

/**
 * Created by Bridge on 2018-05-03.
 */

public class InviteActivity extends BaseActivity implements InviteContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private AlertDialog dialogUtil;
    private InvitePresenter presenter;
    private LinearLayoutManager linearLayoutManager;
    private InviteRecyclerAdapter inviteRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        presenter = new InvitePresenter(this, InviteActivity.this);
        presenter.start();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_invitation;
    }

    @Override
    public void setPresenter(InviteContract.Presenter presenter) {
    }

    // 권한 보여주기
    @Override
    public void showPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionReadContactsResult = checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if (permissionReadContactsResult == PackageManager.PERMISSION_DENIED) {
                /* 사용자가 한번이라도 거부한 적이 있는지 조사해 있다면 true 반환 */
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)
                        && shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)
                        && shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                    dialogUtil = DialogUtil.getDialog(InviteActivity.this, getString(R.string.invitation), getString(R.string.IA_permission_content), yes, intentFundA);
                    dialogUtil.show();
                } else {    /* 최초로 권한을 요청할 때 */
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS}, 1000);
                }
            } else {      /* 권한이 있을 때 */
                presenter.getContacts();
            }
        } else {
            presenter.getContacts();
        }
    }

    // 펀드액티비티로 이동
    @Override
    public void intentFundActivity() {
        dialogUtil = DialogUtil.getDialog(InviteActivity.this, getString(R.string.invitation), getString(R.string.InviteActivity_no_friend), intentFundA);
        dialogUtil.show();
    }

    @Override
    public void setAdapterList(List<InviteItem> list) {
        inviteRecyclerAdapter = new InviteRecyclerAdapter(list);
        recyclerView.setAdapter(inviteRecyclerAdapter);
    }

    // 권한 받아와 처리해주는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /* 권한 획득 완료해야 할 일을 수행한다. */
                if (ActivityCompat.checkSelfPermission(InviteActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(InviteActivity.this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(InviteActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "권한 허가 완료", Toast.LENGTH_SHORT).show();
                    presenter.getContacts();
                }
            } else {
                /* 권한 획득 실패 대안을 찾거나 기능의 수행을 중지한다. */
                dialogUtil = DialogUtil.getDialog(InviteActivity.this, getString(R.string.invitation), getString(R.string.InviteActivity_permission_request), yesInvite, intentFundA);
                dialogUtil.show();
            }
        }
    }

    // 버튼 정의
    @OnClick({R.id.rl_invite, R.id.skip})
    public void onInviteClicked(View v) {
        Intent intent = new Intent(InviteActivity.this, FundActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        switch (v.getId()) {
            case R.id.rl_invite:
                if (presenter.checkCount(inviteRecyclerAdapter) < 5) {
                    dialogUtil = DialogUtil.getDialog(InviteActivity.this, getString(R.string.invitation), getString(R.string.IA_content), dismiss);
                    dialogUtil.show();
                    return;
                }

                presenter.getContactsNamePhone(inviteRecyclerAdapter);

                // 보낸 사람 명단 띄워주면서 감사인사 전하기
                dialogUtil = DialogUtil.getDialog(InviteActivity.this, getString(R.string.ok), presenter.getTotalName(), dismiss);
                dialogUtil.show();
                break;
            case R.id.skip:
                break;
        }
        startActivity(intent);
    }

    // dialog button
    private View.OnClickListener dismiss = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialogUtil.dismiss();
        }
    };
    private View.OnClickListener yes = new View.OnClickListener() {
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS}, 1000);
            }

            dialogUtil.dismiss();
        }
    };
    private View.OnClickListener intentFundA = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(InviteActivity.this, FundActivity.class);
            startActivity(intent);

            dialogUtil.dismiss();
        }
    };
    private View.OnClickListener yesInvite = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            dialogUtil.dismiss();
        }
    };
}
