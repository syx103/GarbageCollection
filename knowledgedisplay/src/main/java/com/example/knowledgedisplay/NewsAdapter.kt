package com.example.knowledgedisplay

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class NewsAdapter(private var newsList: List<NewsListBean>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    fun refreshListData(newList: List<NewsListBean>) {
        val oldSize = newsList.size
        newsList = newList
        val newSize = newList.size
        notifyItemRangeInserted(oldSize, newSize - oldSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.knowledge_display_recyclerview_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemBean = newsList[position]
        itemBean.let {
            holder.tvTitle.text = it.title
            val roundedCorner = RoundedCorners(8)
            val options = RequestOptions.bitmapTransform(roundedCorner)
            Glide.with(holder.itemView.context)
                .load(it.picUrl)
                .apply(options)
                .into(holder.ivCover)
            holder.tvDesc.text = it.ctime
            holder.itemView.setOnClickListener { v ->
                val intent = Intent(v.context, WebviewActivity::class.java).apply {
                    putExtra("url", itemBean.url)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = newsList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvDesc: TextView = view.findViewById(R.id.tv_desc)
        val ivCover: ImageView = view.findViewById(R.id.iv_cover)
    }
}