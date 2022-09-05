package com.campusmap.android.wanted_preonboarding_android.news

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TopNewsService {
    @GET("top-headlines?country=us")
    fun getTopNewsData(@Query("apiKey") apiKey : String) : Call<TopNewsResponse>

    @GET("top-headlines?country=us")
    fun getTopNewsCategoryData(@Query("apiKey") apiKey : String,
                               @Query("category") category: String) : Call<TopNewsResponse>


}