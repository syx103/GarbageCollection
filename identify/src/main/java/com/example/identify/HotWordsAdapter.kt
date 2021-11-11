package com.example.identify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HotWordsAdapter(private var searchList: List<HotWordsListBean>)
    : RecyclerView.Adapter<HotWordsAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun refreshDataList(newList: List<HotWordsListBean>) {
        searchList = newList
        notifyItemRangeChanged(0, newList.size)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        listener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_hot_search_item, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemBean = searchList[position]
        itemBean.let {
            holder.name.text = it.name
            if (it.name == null || "" == it.name) {
                return
            }
            when (it.type) {
                TypeConstant.RECYCLABLE -> holder.type.text = "可回收垃圾"
                TypeConstant.HARMFUL -> holder.type.text = "有害垃圾垃圾"
                TypeConstant.KITCHEN -> holder.type.text = "厨余垃圾"
                TypeConstant.RESIDUAL -> holder.type.text = "其他垃圾"
            }
            holder.itemView.setOnClickListener { _ ->
                listener?.onItemClicked(it.name)
            }
        }
    }

    override fun getItemCount() = searchList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_garbage_name)
        val type: TextView = itemView.findViewById(R.id.tv_type)
    }
}

interface OnItemClickListener {
    fun onItemClicked(name: String)
}