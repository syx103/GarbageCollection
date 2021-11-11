package com.example.identify

data class HotWordsBean(
    val code: Int,
    val msg: String? = null,
    val newslist: List<HotWordsListBean>? = null
)

data class HotWordsListBean(
    val name: String? = null,
    val type: Int,
    val index: Int
)

data class SearchDetailBean(
    val code: Int,
    val msg: String? = null,
    val newslist: List<DetailListBean>? = null
)

data class DetailListBean(
    val name: String? = null,
    val type: Int,
    val aipre: Int,
    val explain: String? = null,
    val contain: String? = null,
    val tip: String? = null
)