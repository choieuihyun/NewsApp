package com.example.data.datasource.remote

import android.content.Context
import com.example.data.db.remote.api.TopNewsApi
import com.example.data.db.remote.response.TopNewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException

class TopNewsDataSource constructor(val context: Context) {

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

    private val topNewsApi: TopNewsApi by lazy {
        retrofit.create(TopNewsApi::class.java)
    }

    suspend fun getTopNewsData() : Response<TopNewsResponse> {

        return topNewsApi.getTopNewsData("2ceb3f8f8c004391b663a3fdcf806c3a")

    }

    suspend fun getTopNewsCategory(
        apiKey : String ,category: String
    ): Response<TopNewsResponse> {

        return topNewsApi.getTopNewsCategoryData("2ceb3f8f8c004391b663a3fdcf806c3a", category)

    }

    companion object { // 필요없어 보이는데

        // 요게 머고
        private var INSTANCE: TopNewsDataSource? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = TopNewsDataSource(context)
            }
        }

        fun get(): TopNewsDataSource {
            return INSTANCE ?:
            throw IllegalStateException("com.example.domain.repository.TopNewsRepository must be initialized")
        }

    }
}