package com.example.administrator.recyclerview.recycler1.recycler1

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.example.administrator.recyclerview.R

class RecyclerAdapter1(val recyclerItem1List: List<RecyclerItem1>) : RecyclerView.Adapter<RecyclerItemViewHolder1>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder1 {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_fragment_1, parent, false)
        return RecyclerItemViewHolder1(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder1, position: Int) {
        holder.bind(recyclerItem1List[position])
    }

    override fun getItemCount(): Int {
        return recyclerItem1List.size
    }
}
