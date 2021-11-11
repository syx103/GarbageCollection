package com.example.login

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HotRequest {
    @GET("?key=?key=ff1a4fc0c802276d58cf569c85dea41e")
    fun getList(): Call<HotResponse>
}