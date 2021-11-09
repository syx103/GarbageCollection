package com.example.knowledgedisplay

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KnowledgeDisplayNetwork {

    private const val baseUrl = "http://api.tianapi.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsService by lazy(LazyThreadSafetyMode.NONE) {
        retrofit.create(KnowledgeDisplayService::class.java)
    }

    fun fetchNewsData(num: Int = 10) = newsService.getNewsInfo(num)
}