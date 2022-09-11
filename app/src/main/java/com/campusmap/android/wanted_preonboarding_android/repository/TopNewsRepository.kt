package com.campusmap.android.wanted_preonboarding_android.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.campusmap.android.wanted_preonboarding_android.BuildConfig
import com.campusmap.android.wanted_preonboarding_android.MainActivity
import com.campusmap.android.wanted_preonboarding_android.news.Article
import com.campusmap.android.wanted_preonboarding_android.news.TopNewsResponse
import com.campusmap.android.wanted_preonboarding_android.news.TopNewsService
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved
import com.campusmap.android.wanted_preonboarding_android.roomdb.SavedDatabase
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException

class TopNewsRepository private constructor(context: Context){ // private constructor 왜 했더라.

    // Architecture Guide: https://developer.android.com/jetpack/guide#connect-viewmodel-repository

    // Retrofit

    private val BASE_URL = "https://newsapi.org/v2/"
    private var topNewsMutableLiveData: MutableLiveData<List<Article?>> = MutableLiveData()
    private var topNewsCategoryMutableLiveData: MutableLiveData<List<Article?>> = MutableLiveData()
    private var topNewsData: List<Article?>? = listOf()


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

/*    fun getTopNewsItemData() : List<Article?>? {
        // 요기 getTopNewsData에서 getString 그냥쓰면 못써서 context 받아와서 사용함.
*//*      Fragment나 Activity에 있는 함수 사용하려면 이렇게 하는듯.
        topNewsService.getTopNewsData(context.resources.getString(BuildConfig.NEWS_API_KEY))*//*
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
                    topNewsData = result
                    Log.d("topNewsData1", topNewsData.toString())
                }
            }
            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("TAG", "fail")
            }

        })
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("topNewsData2", topNewsData.toString())
        }
        return topNewsData
    }*/

    val getTopNewsItemData =
        CoroutineScope(Dispatchers.IO).async {
            val response = topNewsService.getTopNewsDataCoroutine(BuildConfig.NEWS_API_KEY)

            val value = async(Dispatchers.IO) { // Detail 누르면 이 블럭이 종료되는구나.
                if(response.isSuccessful) {
                    val result = response.body()?.articles

                    result.let {
                        topNewsMutableLiveData.postValue(it)
                        topNewsData = it
                    }

                } else {
                    Log.d("TAG", response.code().toString())
                }
                topNewsData
            }
            value.await()
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
                        topNewsCategoryMutableLiveData.postValue(result)
                    }
                }
                override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                    Log.d("TAG", "categoryFail")
                }
            })
    }

    fun getTopNewsAllResponseLiveData(): LiveData<List<Article?>> {
        return this.topNewsMutableLiveData
    }

    fun getTopNewsCategoryAllResponseLiveData(): LiveData<List<Article?>> {
        return this.topNewsCategoryMutableLiveData
    }

    suspend fun getTopNewsAllResponseData(): List<Article?>? {
        return getTopNewsItemData.await()

    }

/*    fun getTopNewsDetailResponseLiveData(position: Int): Article? {
        return this.topNewsMutableLiveData[position]
    }*/


    // Room DB

    private val database : SavedDatabase = Room.databaseBuilder(
        context.applicationContext,
        SavedDatabase::class.java,
        "savedDB"
    )   .fallbackToDestructiveMigration() // 옮길때 데이터 날림
        .build()

    private val savedDao = database.savedDao()

    fun getAllSaved() = savedDao.getAllSavedNewsLiveData() // Saved LiveData 데이터 모두



//    fun getSavedIdData(id: Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//             savedDao.getSavedIdData(id)
//        }
//    }
    suspend fun getTitle(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            savedDao.getSavedNewsData(title)
        }
    }


/*    fun getAllSavedNewsData() = savedDao.getAllSavedNewsData() // 그냥 Saved 데이터 모두

    fun getSavedNewsData() = savedDao.getSavedNewsData()*/

    fun addSaved(saved: Saved) {
        CoroutineScope(Dispatchers.IO).launch {
            savedDao.addSaved(saved)
        }
    }

    fun deleteSaved(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            savedDao.deleteSaved(title)
        }
    }

    fun deleteAllSaved() {
        CoroutineScope(Dispatchers.IO).launch {
            savedDao.deleteAllSaved()
        }
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