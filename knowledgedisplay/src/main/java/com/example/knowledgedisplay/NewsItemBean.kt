package com.example.knowledgedisplay

data class NewsItemBean(
    val code: Int,
    val msg: String,
    val newslist: List<NewsListBean>
)

data class NewsListBean(
    val id: String,
    val ctime: String,
    val title: String,
    val description: String,
    val source: String,
    val picUrl: String,
    val url: String
)