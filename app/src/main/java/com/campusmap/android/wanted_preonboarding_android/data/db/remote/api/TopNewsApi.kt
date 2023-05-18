package com.campusmap.android.wanted_preonboarding_android.data.db.remote.api

import com.campusmap.android.wanted_preonboarding_android.data.db.remote.response.TopNewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TopNewsApi {

    @GET("top-headlines?country=us")
    suspend fun getTopNewsData(@Query("apiKey") apiKey : String) : Response<TopNewsResponse>

    @GET("top-headlines?country=us")
    suspend fun getTopNewsCategoryData(@Query("apiKey") apiKey : String,
                                       @Query("category") category: String) : Response<TopNewsResponse>



}