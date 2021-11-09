package com.example.identify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(val imageLists: MutableList<Int>): RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageFilterView = itemView.findViewById(R.id.banner_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_banner_container,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val index = position % 4
        holder.image.setImageResource(imageLists[index])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}