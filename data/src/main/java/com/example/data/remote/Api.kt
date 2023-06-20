package com.example.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


//base url of website
private const val BASE_URL_ADMIN_PANEL =
    "https://7c66-41-235-173-71.ngrok-free.app/api/"

//base url of AI
private const val BASE_URL_AI_MODEL =
    "https://70c8-105-37-82-227.ngrok-free.app"

private const val BASE_URL_ARD =
    "http://192.168.43.160"

//moshi build which we will use to convert json to object kotlin
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//retrofit build which use to get json response from the base url
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_ADMIN_PANEL)
    .build()

//retrofit build which use to get json response from the base url
private val retrofitARD = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_ARD)
    .build()

// Create an OkHttpClient with a custom timeout
private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(60000, TimeUnit.SECONDS)
    .readTimeout(60000, TimeUnit.SECONDS)
    .writeTimeout(60000,TimeUnit.SECONDS)
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

    val retrofitArdour: ARDApi by lazy {
        retrofitARD.create(ARDApi::class.java)
    }
}