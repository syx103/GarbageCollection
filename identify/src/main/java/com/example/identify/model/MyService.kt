package com.example.identify.model

import com.example.identify.QuestionBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MyService {
    @GET("index")
    fun questionPage(@Query("key") key: String): Observable<QuestionBean>
}