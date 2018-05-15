package com.example.cnwlc.memo.App.main.main.mvp

import android.app.Activity

import com.example.cnwlc.memo.App.main.main.MainItem
import com.example.cnwlc.memo.Util.DateUtil

class MainPresenter(private val view: MainContract.View, private val context: Activity) : MainContract.Presenter {

    override fun start() {}

    override fun setData(mainItemList: MutableList<MainItem>): MutableList<MainItem> {
        val dividedTime = DateUtil.getCurrentTimeYMDAHM().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var time = StringBuilder()
        for (i in dividedTime.indices) {
            if (dividedTime[i] == DateUtil.getCurrentTimeYMD()) {
                time.append(dividedTime[1]).append(" " + dividedTime[2])
                break
            } else {
                time = StringBuilder(dividedTime[i])
            }
        }

        for (i in 0..15) {
            val mainItem = MainItem("MainItem Test_$i", time.toString(), "상")
            mainItemList.add(mainItem)
        }

        return mainItemList
    }
}
