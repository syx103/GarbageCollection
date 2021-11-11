package com.example.identify

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object GarbageSearchNetwork {
    private const val baseUrl = "http://api.tianapi.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val searchService by lazy(LazyThreadSafetyMode.NONE) {
        retrofit.create(GarbageSearchService::class.java)
    }

    fun fetchHotWords() = searchService.getHotWords()

    fun fetchGarbageDetail(word: String) = searchService.getGarbageDetail(word)
}