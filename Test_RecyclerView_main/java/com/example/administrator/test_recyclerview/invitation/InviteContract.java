package com.example.administrator.test_recyclerview.invitation;

import com.example.administrator.test_recyclerview.BasePresenter;
import com.example.administrator.test_recyclerview.BaseView;

import java.util.List;

public interface InviteContract {

    interface View extends BaseView<Presenter> {
        void showPermission();      // 권한 보여주기
        void intentFundActivity();  // intent 로 이동
        void setAdapterList(List<InviteItem> list);  // 어댑터에 리스트 넣어
    }

    interface Presenter extends BasePresenter {
        void getContacts();         // 사용자 명단 전화번호부에서 가져오기
        void getContactsNamePhone(InviteRecyclerAdapter inviteRecyclerAdapter);    // 메시지 전송하기 위한 메소드
    }
}
