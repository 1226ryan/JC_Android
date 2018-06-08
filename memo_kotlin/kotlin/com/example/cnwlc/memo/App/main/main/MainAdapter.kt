package com.example.cnwlc.memo.App.main.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.cnwlc.memo.R

/**
 * Created by Bridge on 2018-05-06.
 */


class MainAdapter(private val mainItems: MutableList<MainItem>) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return MainViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(mainItems[position])
    }

    override fun getItemCount(): Int {
        return mainItems.size
    }
}
