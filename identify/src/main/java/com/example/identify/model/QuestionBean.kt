package com.example.identify

data class QuestionBean(
    val code: Int,
    val msg: String,
    val newslist: List<NewsList>,
    var selectAnswer: String
)
data class NewsList(
    val name: String,
    val type: Int,
    val explain: String
)