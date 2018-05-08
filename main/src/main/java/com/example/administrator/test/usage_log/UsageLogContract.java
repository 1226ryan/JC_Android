package com.example.administrator.test.usage_log;

import java.util.List;

public interface UsageLogContract {
    interface View extends BaseView<Presenter> {
        void showRecycler(List<UsageLogListItem> list);         // recyclerView 에 데이터 넣기
        void pagingView(int listCount, int OFFSET, List<UsageLogListItem> list);        // paging 에 관한 view 변경 사항 확인
    }
    interface Presenter extends BasePresenter{
        void getUsageDetailItem(int pageNumber);        // 이용내역 서버에서 받아오기
    }
}
