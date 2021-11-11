package com.example.identify.model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageBase64Service {
    @GET("index")
    fun imageBase64(@Query("key") key: String,@Query("img") img: String):Observable<ImageBase64Bean>
}