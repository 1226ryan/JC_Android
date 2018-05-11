package com.example.administrator.recyclerview.recycler2

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.example.administrator.recyclerview.R

class RecyclerItemViewHolder2(itemView: View, private val itemClick: (RecyclerItem2) -> Unit) : RecyclerView.ViewHolder(itemView) {
    /* (2) Holder에서 클릭에 대한 처리를 할 것이므로, Holder의 파라미터에 람다식 itemClick을 넣는다. */
    private val chatName = itemView.findViewById<TextView>(R.id.recycler_fragment2_text_view_name)
    private val chatMessage = itemView.findViewById<TextView>(R.id.recycler_fragment2_text_view_message)
    private val chatPhoto = itemView.findViewById<ImageView>(R.id.recycler_fragment2_image_view_photo)

    fun bind(recyclerItem2List: RecyclerItem2, context: Context?) {
        val resouceId = context?.resources?.getIdentifier(recyclerItem2List.photo, "drawable", context.packageName)
        chatPhoto?.setImageResource(resouceId!!)
        chatName?.text = recyclerItem2List.name
        chatMessage?.text = recyclerItem2List.message

        itemView.setOnClickListener { itemClick(recyclerItem2List) }
        /* (3) itemView가 클릭됐을 때 처리할 일을 itemClick 으로 설정한다.
             * (Chat) -> Unit 에 대한 함수는 나중에 MainActivity.kt 클래스에서 작성한다. */
    }
}
