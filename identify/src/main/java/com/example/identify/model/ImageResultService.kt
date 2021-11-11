package com.example.identify.model

import io.reactivex.Observable
import retrofit2.http.*

interface ImageResultService {
  /*  fun imageRecognitionResult(
        @Query("key") key: String,
        @Query("img") img: String,
        @Query("mode") mode: Int
    ): Observable<TakePhotoBean>*/

    @FormUrlEncoded
    @POST("recover")
    fun imageRecognitionResult(@Field("img") img: String, @Header("Authorization") authorization: String): Observable<TakePhotoBean>
}