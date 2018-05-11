package com.example.administrator.recyclerview.recycler2

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.example.administrator.recyclerview.R

// 어댑터에 대한 constructor로 context 와, 커스텀클래스 RecyclerItem2 의 변수를 모아둔 List 인 recyclerItem2List 을 사용
/* (1) Adapter의 파라미터에 람다식 itemClick을 넣는다. */
class RecyclerAdapter2(private val context: Context?,
                       private val recyclerItem2List: List<RecyclerItem2>,
                       private val itemClick: (RecyclerItem2) -> Unit) : RecyclerView.Adapter<RecyclerItemViewHolder2>() {
    /*
    RecyclerItem2 을 파라미터로 받아서, 아무것도 반환하지 않는 파라미터를 람다식으로 나타낸 것

    RecyclerItem2 을 파라미터로 사용하고, 아무것도 반환하지 않는(Unit) 기능을 하는 함수 자체를 itemClick 변수에 넣는다.
    그 itemClick 변수를 Adapter의 파라미터로 넣고, 어댑터 내에서 setOnClickListener 기능을 설정할 때
    (RecyclerItem2) -> Unit에 해당하는 함수 자체를 하나의 변수로 꺼내 쓸 수 있는 것이다.
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder2 {
        val rootView = LayoutInflater.from(context).inflate(R.layout.item_recycler_fragment_2, parent, false)
        return RecyclerItemViewHolder2(rootView, itemClick)
        /* (4) Holder의 파라미터가 하나 더 추가됐으므로, 이곳에도 추가해준다. */
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder2, position: Int) {
        holder.bind(recyclerItem2List[position], context)
    }

    override fun getItemCount(): Int {
        return recyclerItem2List.size
    }
}
