package com.example.cnwlc.memo.App.main.main.mvp

import com.example.cnwlc.memo.App.main.main.MainItem
import com.example.cnwlc.memo.Common.BasePresenter
import com.example.cnwlc.memo.Common.BaseView

interface MainContract {
    interface View : BaseView<Presenter>
    interface Presenter : BasePresenter {
        fun setData(mainItemList: MutableList<MainItem>): MutableList<*>       // data set 하고 return 하기 위한 함수 정의
    }
}
