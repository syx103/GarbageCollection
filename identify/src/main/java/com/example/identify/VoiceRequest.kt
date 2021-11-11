package com.example.identify

import retrofit2.http.POST

interface VoiceRequest {
    @POST
    fun getVoice()

}