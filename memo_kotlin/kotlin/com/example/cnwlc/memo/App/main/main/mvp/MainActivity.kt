package com.example.cnwlc.memo.App.main.main.mvp

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.InputMethodManager

import com.example.cnwlc.memo.App.main.main.MainAdapter
import com.example.cnwlc.memo.App.main.main.MainItem
import com.example.cnwlc.memo.Common.BaseActivity
import com.example.cnwlc.memo.R
import com.example.cnwlc.memo.Util.ToastUtil

import java.util.ArrayList

import com.example.cnwlc.memo.App.write.write.mvp.WriteActivity

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

/**
 * Created by Bridge on 2018-05-15.
 */


class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var mainItemList : MutableList<MainItem>
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MainPresenter(this, this@MainActivity)

        initView()
        onClickView()
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this)
        mainA_recycler_view.layoutManager = linearLayoutManager

        mainItemList = ArrayList()
        val mainAdapter = MainAdapter(presenter.setData(mainItemList))
        mainA_recycler_view.adapter = mainAdapter

        mainA_text_view_number_of_memos.text = (mainAdapter.itemCount.toString()+"개의 메모")
    }

    private fun onClickView() {
        mainA_relative_layout_back.setOnClickListener { ToastUtil.shortToast(this, "wait...") }
        mainA_image_view_add_memo.setOnClickListener { startActivity<WriteActivity>() }     // startActivityForResult 로 수정해서 받아오기
        mainA_text_view_edit.setOnClickListener { ToastUtil.shortToast(this, "wait...") }
        mainA_text_view_search.setOnClickListener {
            val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    override fun setPresenter(presenter: MainContract.Presenter) {}
}