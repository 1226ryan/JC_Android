package com.example.cnwlc.memo.App.write.write.mvp

import android.os.Bundle

import com.example.cnwlc.memo.Common.BaseActivity
import com.example.cnwlc.memo.R
import com.example.cnwlc.memo.Util.DateUtil
import com.example.cnwlc.memo.Util.ToastUtil

import kotlinx.android.synthetic.main.activity_write.*

class WriteActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        onClick()
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_write
    }

    private fun initView() {
        writeA_text_view_time.text = DateUtil.getCurrentTimeYMDAHM()
    }

    private fun onClick() {
        writeA_relative_layout_back.setOnClickListener { finish() }
        writeA_text_view_completion.setOnClickListener { ToastUtil.shortToast(this, "wait...") }
    }
}
