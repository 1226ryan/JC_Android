package com.example.administrator.recyclerview.recycler1.recycler1

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.administrator.recyclerview.R

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_recycler1.*

class RecyclerFragment1 : Fragment() {
    /** 생명주기 관련 참고 싸이트 : http://webnautes.tistory.com/1089  */

    private lateinit var recyclerItem1List: MutableList<RecyclerItem1>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var recyclerAdapter1: RecyclerAdapter1

    private lateinit var selectedName : String

    companion object {
        fun newInstance(): RecyclerFragment1 {
            return RecyclerFragment1()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerFragment1RecyclerView.layoutManager = linearLayoutManager

        recyclerItem1List = ArrayList()
        for (i in 0 until 20) {
            val recyclerItem1 = RecyclerItem1("RecyclerFragment1 Test_$i", false)
            recyclerItem1List.add(recyclerItem1)
        }
        recyclerAdapter1 = RecyclerAdapter1(recyclerItem1List)
        RecyclerFragment1RecyclerView.adapter = recyclerAdapter1

        clickListener()
    }

    private fun getName(recyclerAdapter1: RecyclerAdapter1?): String {
        selectedName = ""
        val listName = getContactsName(recyclerAdapter1)
        for (i in listName.indices) {
            selectedName += listName[i].toString() + "\n"
        }

        return selectedName
    }

    // 이름을 activity 로 리턴
    private fun getContactsName(recyclerAdapter1: RecyclerAdapter1?): List<String> {
        val list = recyclerAdapter1!!.recyclerItem1List
        val listName = ArrayList<String>()

        for (i in list.indices) {
            val (name, isChecked) = list[i]
            if (isChecked) {
                listName.add(name)
            }
        }
        return listName
    }

    private fun clickListener() {
        RecyclerFragment1Button.setOnClickListener { println("getName(recyclerAdapter1) : " + getName(recyclerAdapter1)) }
    }
}
