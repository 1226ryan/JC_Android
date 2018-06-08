package com.example.cnwlc.memo.App.main.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.example.cnwlc.memo.R

/**
 * Created by Bridge on 2018-05-06.
 */

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvTitle: TextView = itemView.findViewById(R.id.memo_item_textView_title)
    var tvImport: TextView = itemView.findViewById(R.id.memo_item_textView_import)
    var tvDaily: TextView = itemView.findViewById(R.id.memo_item_textView_daily)

    fun bind(mainItem: MainItem) {
        tvTitle.text = mainItem.sTitle
        tvImport.text = mainItem.sImport
        tvDaily.text = mainItem.sDaily
    }
}
