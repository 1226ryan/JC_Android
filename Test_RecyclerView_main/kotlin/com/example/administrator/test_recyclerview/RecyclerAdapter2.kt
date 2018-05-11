package com.example.administrator.test_recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class RecyclerAdapter2(val items: ArrayList<RecyclerItem2>) : RecyclerView.Adapter<RecyclerAdapter2.ItemViewHolder2>() {
    var focusedItem: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        return ItemViewHolder2(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder2, position: Int) {
        holder.bind(items.get(position))
    }

    inner class ItemViewHolder2(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val mNameTv = itemView?.findViewById<TextView>(R.id.itemNameTv)
        val mImageView = itemView?.findViewById<ImageView>(R.id.itemImageView)

        fun bind(item: RecyclerItem2) {
            mNameTv?.text = item.name
            mImageView?.setImageResource(item.imageResource)
        }
    }




//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//
//        recyclerView.setOnKeyListener(new View.)
//    }
//
//    private fun tryMoveSelection(lm : RecyclerView.LayoutManager, direction: Int): Boolean {
//        var tryFocusItem = focusedItem + direction
//
//        if( tryFocusItem >= 0 && tryFocusItem < itemCount) {
//            notifyItemChanged(focusedItem)
//            focusedItem = tryFocusItem
//            notifyItemChanged(focusedItem)
//            lm.scrollToPosition(focusedItem)
//            return true
//        }
//
//        return false
//    }
}