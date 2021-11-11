package com.example.identify.model

data class ImageBase64Bean(
    val message: String,
    val code: Int,
    val newslist: MutableList<ImageBase64NewsList>
)
data class ImageBase64NewsList(
    val imgdata: String
)