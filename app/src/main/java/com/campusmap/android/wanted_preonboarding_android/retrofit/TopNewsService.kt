package com.campusmap.android.wanted_preonboarding_android.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TopNewsService {
    @GET("top-headlines?country=us")
    fun getTopNewsData(@Query("apiKey") apiKey : String) : Call<TopNewsResponse>

    @GET("top-headlines?country=us")
    suspend fun getTopNewsDataCoroutine(@Query("apiKey") apiKey : String) : Response<TopNewsResponse>

    @GET("top-headlines?country=us")
    suspend fun getTopNewsCategoryData(@Query("apiKey") apiKey : String,
                               @Query("category") category: String) : Response<TopNewsResponse>




}