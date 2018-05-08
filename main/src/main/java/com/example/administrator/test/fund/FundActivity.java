package com.example.administrator.test.fund;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import co.bitpartner.R;
import co.bitpartner.app.BottomNavigationBaseActivity;
import co.bitpartner.app.common.Defines;
import co.bitpartner.app.fund.fund_rec_detail.FundRecDetailActivity;
import co.bitpartner.data.model.FundAccumuldateBTC;
import co.bitpartner.data.model.FundList;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.DecimalFormatUtil;
import co.bitpartner.util.DialogUtilNotice;
import co.bitpartner.util.SharedPreferenceUtil;


public class FundActivity extends BottomNavigationBaseActivity
        implements FundRecyclerAdapter.FundDisjoinCallback, FundContract.View {

    @BindView(R.id.fund_recycler)
    RecyclerView fundRecycler;

    @BindView(R.id.no_fund_msg)
    TextView noFundMsg;

    @BindView(R.id.bottom_navi)
    BottomNavigationView bottomNavi;

    @BindView(R.id.btc)
    TextView btc;

    @BindView(R.id.currency)
    TextView currency;

    @BindView(R.id.market_price)
    TextView marketPrice;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.rl_all_invest_fund)
    RelativeLayout rlAllFundInvest;

    @BindView(R.id.krw)
    TextView countryCurrency;

//    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout refreshLayout;

    public static void start(Context context) {
        Intent intent = new Intent(context, FundActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        BottomNavigationBaseActivity.oldId = -1;
    }

    private DialogUtilNotice dialogUtilNotice;
    private FundRecyclerAdapter fundAdapter;
    private FundPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FundPresenter(FundRepository.getInstance(), this, this);
        presenter.processFundMenuCheck(bottomNavi.getMenu());
        showProgressbar();

        //최초 한번만 실행
        Intent intent = getIntent();
        List<String> notices = intent.getStringArrayListExtra(Defines.EXTRA_NOTICE);
        if (notices != null)
            presenter.processNoticeTimeDelay(notices);
    }


    @Override
    protected int getNavigationMenuItemId() {
        return R.id.fund;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fund;
    }

    private void setSwipeRefresh() {
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getFundsInOffer();
//                refreshLayout.setRefreshing(false);
//            }
//        });
    }

    @Override
    public void initView() {
        fundAdapter = new FundRecyclerAdapter(Defines.TYPE_NOT_JOIN_FUND);
        fundAdapter.setCallback(this);
        fundRecycler.setNestedScrollingEnabled(false);
        fundRecycler.setLayoutManager(new LinearLayoutManager(this));
        fundRecycler.setAdapter(fundAdapter);
    }

    @Override
    public void showFundMenuCheck(MenuItem menuItem) {
        menuItem.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAllFundInvest(FundAccumuldateBTC fundAccumuldateBTC, double countryCurrencyPrice) {
        rlAllFundInvest.setVisibility(View.VISIBLE);

        btc.setText(DecimalFormatUtil.getFormatNumber(Double.parseDouble(fundAccumuldateBTC.count)));
        currency.setText(fundAccumuldateBTC.name);
        if(SharedPreferenceUtil.getInstance().getCountryCurrency().equals("KRW")) {
            marketPrice.setText(DecimalFormatUtil.getFormat((int) countryCurrencyPrice));
        } else {
            marketPrice.setText(DecimalFormatUtil.getFormat(Double.parseDouble(String.format("%.2f",countryCurrencyPrice))));
        }
        countryCurrency.setText(SharedPreferenceUtil.getInstance().getCountryCurrency());
    }

    @Override
    public void showRecommandFund(List<FundList> funds) {
        noFundMsg.setVisibility(View.GONE);
        fundAdapter.setFundsInOffer(funds);
    }

    @Override
    public void showEmptyFundMsg() {
        noFundMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNotice(List<String> list) {
        if(list.size() >= 3) {
            dialogUtilNotice = DialogUtilNotice.getDialog(this, list, closeInWeekListener, yesListener);
            dialogUtilNotice.show();
        }
    }

    private View.OnClickListener yesListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogUtilNotice.dismiss();
        }
    };

    private View.OnClickListener closeInWeekListener = view -> {
        presenter.saveCloseTime();
        dialogUtilNotice.dismiss();
    };

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stop();
        hideProgressbar();
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFundDetailClicked(FundList fund) {
        FundRecDetailActivity.start(this, fund);
    }

    @Override
    public void setPresenter(FundContract.Presenter presenter) {
    }
}
