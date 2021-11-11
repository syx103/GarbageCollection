package com.example.identify

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class SearchResultDialog(
    context: Context,
    private val name: String?,
    private val explain: String?,
    private val contain: String?,
    private val type: Int
) : Dialog(context){

    private lateinit var tvName: TextView
    private lateinit var ivIcon: ImageView
    private lateinit var tvType: TextView
    private lateinit var tvExplain: TextView
    private lateinit var tvContain: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_search_result_dialog)
        initView()
    }

    private fun initView() {
        tvName = findViewById(R.id.tv_name)
        ivIcon = findViewById(R.id.iv_icon)
        tvType = findViewById(R.id.tv_type)
        tvExplain = findViewById(R.id.tv_explain)
        tvContain = findViewById(R.id.tv_contain)
        setCancelable(true)

        tvName.text = name
        tvExplain.text = explain
        tvContain.text = contain
        when (type) {
            TypeConstant.RECYCLABLE -> {
                tvName.setBackgroundColor(context.resources.getColor(R.color.blue_recyclable))
                tvType.text = "可回收垃圾"
                ivIcon.setImageResource(R.drawable.icon_recycleable)
            }
            TypeConstant.HARMFUL -> {
                tvName.setBackgroundColor(context.resources.getColor(R.color.red_harmful))
                tvType.text = "有害垃圾"
                ivIcon.setImageResource(R.drawable.icon_harmful)
            }
            TypeConstant.KITCHEN -> {
                tvName.setBackgroundColor(context.resources.getColor(R.color.green_kitchen))
                tvType.text = "厨余垃圾"
                ivIcon.setImageResource(R.drawable.icon_kitchen)
            }
            TypeConstant.RESIDUAL -> {
                tvName.setBackgroundColor(context.resources.getColor(R.color.black_residual))
                tvType.text = "其他垃圾"
                ivIcon.setImageResource(R.drawable.icon_residual)
            }
        }
    }
}