package com.example.data.remote

import com.example.domain.entity.AiResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiAI{
    @Multipart
    @POST("upload/")
    suspend fun detectProduct(@Part image: MultipartBody.Part?) : AiResponse
}