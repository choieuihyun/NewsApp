package com.campusmap.android.wanted_preonboarding_android.repository

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.campusmap.android.wanted_preonboarding_android.BuildConfig
import com.campusmap.android.wanted_preonboarding_android.MainActivity
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.RetrofitClient
import com.campusmap.android.wanted_preonboarding_android.news.Article
import com.campusmap.android.wanted_preonboarding_android.news.TopNewsResponse
import com.campusmap.android.wanted_preonboarding_android.news.TopNewsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Tag
import java.lang.IllegalStateException
import kotlin.coroutines.coroutineContext

class TopNewsRepository private constructor(context: Context){

    private val BASE_URL = "https://newsapi.org/v2/"
    private var topNewsMutableLiveData: MutableLiveData<List<Article?>> = MutableLiveData()

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
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

    private val topNewsService: TopNewsService by lazy {
        retrofit.create(TopNewsService::class.java)
    }

/*    private val topNewsService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()
        .create(TopNewsService::class.java)*/

    fun getTopNewsItemData(context: Context) {
        // 요기 getTopNewsData에서 getString 그냥쓰면 못써서 context 받아와서 사용함.
/*      Fragment나 Activity에 있는 함수 사용하려면 이렇게 하는듯.
        topNewsService.getTopNewsData(context.resources.getString(BuildConfig.NEWS_API_KEY))*/
        topNewsService.getTopNewsData(BuildConfig.NEWS_API_KEY)
            .enqueue(object : Callback<TopNewsResponse>{
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("TAG", response.body().toString())
                    val result = response.body()?.articles
                    topNewsMutableLiveData.postValue(result)
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("TAG", "fail")
            }

        })
    }

    fun getCategoryItemData(context: Context, category: String) {

        val activity : Context = context

        (activity as MainActivity).supportActionBar?.title = "Category - ${category.replaceFirstChar { it.uppercase() }}"

        topNewsService.getTopNewsCategoryData(BuildConfig.NEWS_API_KEY, category)
            .enqueue(object : Callback<TopNewsResponse> {
                override fun onResponse(
                    call: Call<TopNewsResponse>,
                    response: Response<TopNewsResponse>
                ) {
                    if(response.isSuccessful) {
                        val result = response.body()?.articles
                        topNewsMutableLiveData.postValue(result)
                    }
                }

                override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                    Log.d("TAG", "categoryFail")
                }
            })
    }

    fun getTopNewsResponseLiveData(): LiveData<List<Article?>> {
        return this.topNewsMutableLiveData
    }

    companion object {

        private var INSTANCE: TopNewsRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = TopNewsRepository(context)
            }
        }

        fun get(): TopNewsRepository {
            return INSTANCE ?:
            throw IllegalStateException("TopNewsRepository must be initialized")
        }

    }


}