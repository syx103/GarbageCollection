package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HotAdapter(private val list :List<ItemModel>) :RecyclerView.Adapter<HotAdapter.HotViewHolder>() {


    inner class HotViewHolder(view :View) :RecyclerView.ViewHolder(view) {
        var index = view.findViewById<TextView>(R.id.item_index)
        val content = view.findViewById<TextView>(R.id.item_content)
        val addContent = view.findViewById<TextView>(R.id.item_add_content)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return HotViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotViewHolder, position: Int) {
        val itemModel = list[position]
        holder.index.text = itemModel.hotIndex.toString()
        holder.addContent.text = when(itemModel.hotType) {
            0->"可回收垃圾"
            1->"有害垃圾"
            2->"厨余垃圾"
            3->"其他垃圾"
            else -> "无"
        }
        holder.content.text = itemModel.hotName
    }

    override fun getItemCount(): Int = list.size
}