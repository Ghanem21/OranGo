package com.example.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


//base url of website
private const val BASE_URL_ADMIN_PANEL =
    "https://8f5b-105-41-218-121.ngrok-free.app/api/"

//base url of AI
private const val BASE_URL_AI_MODEL =
    "https://8f5b-105-41-218-121.ngrok-free.app/"

//moshi build which we will use to convert json to object kotlin
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//retrofit build which use to get json response from the base url
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_ADMIN_PANEL)
    .build()

// Create an OkHttpClient with a custom timeout
private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(6000, TimeUnit.SECONDS)
    .readTimeout(6000, TimeUnit.SECONDS)
    .build()

//retrofit build which use to get json response from the base url
private val retrofitAIModel = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_AI_MODEL)
    .client(okHttpClient)
    .build()

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val retrofitServiceForAI: ApiAI by lazy {
        retrofitAIModel.create(ApiAI::class.java)
    }
}