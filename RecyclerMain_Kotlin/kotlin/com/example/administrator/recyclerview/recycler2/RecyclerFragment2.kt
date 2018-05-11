package com.example.administrator.recyclerview.recycler2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.administrator.recyclerview.R

import kotlinx.android.synthetic.main.fragment_recycler2.*
import org.jetbrains.anko.support.v4.longToast

class RecyclerFragment2 : Fragment() {
    /* https://nachoi.github.io/studynote/2017/12/06/Android-Kotlin-RecyclerView1.html */

    private lateinit var recyclerItem2List: MutableList<RecyclerItem2>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var recyclerAdapter2: RecyclerAdapter2

    companion object {
        fun newInstance(): RecyclerFragment2 {
            return RecyclerFragment2()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerFragment2RecyclerView.layoutManager = linearLayoutManager
        RecyclerFragment2RecyclerView.setHasFixedSize(true)
        // setHasFixedSize(true)인 이유는 item이 추가되거나 삭제될 때 RecyclerView의 크기가 변경될 수도 있고, 그렇게 되면 계층 구조의 다른 View 크기가 변경될 가능성이 있기 때문이다.
        // 특히 item이 자주 추가/삭제되면 오류가 날 수도 있기에 setHasFixedSize true를 설정한다.

        recyclerItem2List = arrayListOf(
                RecyclerItem2("Title_1", "content_1", "test_1"),
                RecyclerItem2("Title_2", "content_2", "test_2"),
                RecyclerItem2("Title_3", "content_3", "test_3"),
                RecyclerItem2("Title_4", "content_4", "test_1"),
                RecyclerItem2("Title_5", "content_5", "test_2"),
                RecyclerItem2("Title_6", "content_6", "test_3"),
                RecyclerItem2("Title_7", "content_7", "test_1"),
                RecyclerItem2("Title_8", "content_8", "test_2"),
                RecyclerItem2("Title_9", "content_9", "test_3")
        )

        recyclerAdapter2 = RecyclerAdapter2(context, recyclerItem2List) { recyclerItem2List ->
            longToast("${recyclerItem2List.name}님이 보낸 메시지 - ${recyclerItem2List.message}")
        }
        /* (5) Adapter의 파라미터를 추가했으므로, (context, recyclerItem2List)에서 (context, recyclerItem2List, Lambda)가 되어야한다.
         * 코틀린에서 맨 마지막 파라미터가 람다식일 경우, 코드의 가독성을 위해서 괄호() 뒤로 람다식을 빼고 대괄호{}로 묶는다.
         * {(recyclerItem2List) -> Unit} 에 대한 내용을 Toast를 실행하는 것으로 만들었다.
         * */
        RecyclerFragment2RecyclerView.adapter = recyclerAdapter2
    }
}
