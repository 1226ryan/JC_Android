package com.example.administrator.test_recyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.administrator.test_recyclerview.R.id.kotlinRecyclerView
import kotlinx.android.synthetic.main.activity_main_2.*

class MainActivity2 : AppCompatActivity() {
    private val names : List<String> = listOf("Charlie", "Andrew", "Han", "Liz", "Thomas", "Sky", "Andy", "Lee", "Park")
    private val resources : List<Int> = listOf(R.drawable.buy_3, R.drawable.kakao, R.drawable.noti_setting)

    private var mItems = ArrayList<RecyclerItem2>()
    private var recyclerAdapter2 = RecyclerAdapter2(mItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_2)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        kotlinRecyclerView.setHasFixedSize(true)
        kotlinRecyclerView.adapter = recyclerAdapter2
        kotlinRecyclerView.layoutManager = LinearLayoutManager(this)

        val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation)
        kotlinRecyclerView.addItemDecoration(dividerItemDecoration)

        setData()
    }

    private fun setData() {
        mItems.clear()

        for(name:String in names) {
            for(resource:Int in resources) {
                mItems.add(RecyclerItem2(name, resource))
            }
        }

        for(name:String in names) {
            for(resource:Int in resources) {
                mItems.add(RecyclerItem2(name, resource))
            }
        }

        for(name:String in names) {
            for(resource:Int in resources) {
                mItems.add(RecyclerItem2(name, resource))
            }
        }

        recyclerAdapter2.notifyDataSetChanged()
    }
}