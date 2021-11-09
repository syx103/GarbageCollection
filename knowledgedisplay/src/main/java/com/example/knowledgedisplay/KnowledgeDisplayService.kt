package com.example.knowledgedisplay

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface KnowledgeDisplayService {
    @GET("lajifenleinews/index/index?key=eeac9836779da22cc5ca9ed8e34be425&num={news_num}")
    fun getNewsInfo(@Path("news_num") num: Int = 10) : Observable<NewsItemBean>
}