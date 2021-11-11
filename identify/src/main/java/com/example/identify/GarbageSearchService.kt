package com.example.identify

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GarbageSearchService {

    @GET("hotlajifenlei/index?key=eeac9836779da22cc5ca9ed8e34be425")
    fun getHotWords(): Observable<HotWordsBean>

    @GET("lajifenlei/index")
    fun getGarbageDetail(
        @Query("word") word: String,
        @Query("key") key: String = KEY
    ): Observable<SearchDetailBean>

    companion object {
        const val KEY = "eeac9836779da22cc5ca9ed8e34be425"
    }
}