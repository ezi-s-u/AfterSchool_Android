package com.study.hozuandroidstudy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.study.hozuandroidstudy.api.Post

class PostListAdapter(val dataList : List<Post>) : RecyclerView.Adapter<PostListAdapter.ItemViewHolder>() {
     class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) // 한 항목이 view

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder { // viewType == post_list_item
          // inflate를 하면 layout.xml파일을 view 객체로 만들어준다
          val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
          return ItemViewHolder(view)
     }

     // 항목이 3개면 3번 호출되는
     override fun onBindViewHolder(holder: ItemViewHolder, position: Int) { // holder == return 받은 ItemViewHolder, position == 만든 항목의 위치
          val item = dataList[position]
          val view = holder.view
          view.findViewById<TextView>(R.id.post_title).text = item.title
          view.setOnClickListener {
               val intent = Intent(view.context, PostReadActivity::class.java)
               intent.putExtra("id", dataList[holder.adapterPosition].id)
               view.context.startActivity(intent)
          }
     }

     override fun getItemViewType(position: Int) = R.layout.post_list_item // 항목 표시할 때 필요한 항목의 레이아웃 리소스의 id를 반환한다

     override fun getItemCount() = dataList.size
}