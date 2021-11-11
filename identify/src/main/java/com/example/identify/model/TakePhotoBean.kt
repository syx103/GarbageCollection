package com.example.identify.model

data class TakePhotoBean(
    val ret: Int,
    val data: MutableList<ResultNewsList>
)
data class ResultNewsList(
    val keyword: String,
    val list: MutableList<DataList>
)
data class DataList(
    val name: String,
    val category: String
)