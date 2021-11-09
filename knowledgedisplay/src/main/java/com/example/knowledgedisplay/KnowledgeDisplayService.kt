package com.example.knowledgedisplay

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface KnowledgeDisplayService {
    @GET("lajifenleinews/index?key=eeac9836779da22cc5ca9ed8e34be425&num=10")
    fun getNewsInfo() : Observable<NewsItemBean>
}