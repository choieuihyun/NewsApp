package com.campusmap.android.wanted_preonboarding_android

import com.campusmap.android.wanted_preonboarding_android.retrofit.TopNewsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




object RetrofitClient { // singleton

    var BASE_URL = "https://newsapi.org/v2/"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    val topNewsService: TopNewsService by lazy {
        retrofit.create(TopNewsService::class.java)
    }

}