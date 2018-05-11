package com.example.administrator.recyclerview.recycler1.recycler1

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView

import com.example.administrator.recyclerview.R

class RecyclerItemViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvRecyclerFragment1Name = itemView.findViewById<TextView>(R.id.RecyclerFragment1TextViewName)
    var cbRecyclerFragment1 = itemView.findViewById<CheckBox>(R.id.RecyclerFragment1CheckBox)

    fun bind(recyclerItem1List: RecyclerItem1) {
        tvRecyclerFragment1Name.text = recyclerItem1List.name
        cbRecyclerFragment1.isChecked = recyclerItem1List.isChecked
        cbRecyclerFragment1.tag = recyclerItem1List

        cbRecyclerFragment1.setOnClickListener {
            val checkBox = it as CheckBox
            val recyclerItem1 = checkBox.tag as RecyclerItem1

            recyclerItem1.isChecked = checkBox.isChecked
            recyclerItem1List.isChecked = checkBox.isChecked
        }
    }
}
