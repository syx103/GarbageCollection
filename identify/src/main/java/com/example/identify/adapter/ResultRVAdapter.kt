package com.example.identify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.identify.R
import com.example.identify.model.DataList
import com.example.identify.model.ResultNewsList

class ResultRVAdapter(private val resultLists: List<DataList>): RecyclerView.Adapter<ResultRVAdapter.ViewHolder>() {
    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val resultName: TextView = item.findViewById(R.id.result_name)
        val resultExplain: TextView = item.findViewById(R.id.result_explain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_recognition_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemBean = resultLists[position]
        holder.resultName.text = itemBean.name
        holder.resultExplain.text = itemBean.category
    }

    override fun getItemCount() = resultLists.size
}