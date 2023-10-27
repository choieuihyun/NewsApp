package com.campusmap.android.wanted_preonboarding_android.data.repositoryimpl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.campusmap.android.wanted_preonboarding_android.BuildConfig
import com.campusmap.android.wanted_preonboarding_android.data.datasource.local.TopNewsSavedDataSource
import com.campusmap.android.wanted_preonboarding_android.data.datasource.remote.TopNewsDataSource
import com.campusmap.android.wanted_preonboarding_android.data.db.remote.response.Article
import com.campusmap.android.wanted_preonboarding_android.data.db.local.entity.Saved
import com.campusmap.android.wanted_preonboarding_android.domain.repository.TopNewsRepository
import kotlinx.coroutines.*
import java.lang.IllegalStateException

const val apiKey = BuildConfig.NEWS_API_KEY
class TopNewsRepositoryImpl private constructor(
    context: Context
) : TopNewsRepository{

    // Architecture Guide: https://developer.android.com/jetpack/guide#connect-viewmodel-repository

    // Retrofit
    private val topNewsDataSource = TopNewsDataSource(context)

    // Room DB
    private val topNewsSavedDataSource = TopNewsSavedDataSource(context)


/*
    val getTopNewsItemData =
        CoroutineScope(Dispatchers.IO).async {
            val response = topNewsApi.getTopNewsDataCoroutine(BuildConfig.NEWS_API_KEY)

            val value = async(Dispatchers.IO) {
                if (response.isSuccessful) {
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
*/
    override suspend fun getTopNewsResponseData() {
        topNewsDataSource.getTopNewsData(apiKey)
    }

    override suspend fun getTopNewsAllResponseData(): List<Article?>? {
        return topNewsDataSource.topNewsData
    }

    override suspend fun getTopNewsLiveData(): MutableLiveData<List<Article?>> {
        return topNewsDataSource.topNewsMutableLiveData
    }


    override suspend fun getCategoryResponseData(category: String) {
        topNewsDataSource.getTopNewsCategoryData(apiKey, category)
    }


    override suspend fun getTopNewsCategoryAllResponseLiveData(): LiveData<List<Article?>> {
        return topNewsDataSource.topNewsMutableLiveData
    }

    override suspend fun getTopNewsCategoryAllResponseData(): List<Article?>? { // category detail data
        return topNewsDataSource.topNewsCategoryData
    }


    // Saved
    override fun getAllSavedLiveData() = topNewsSavedDataSource.getAllSavedLiveData
    override fun getAllSavedData() = topNewsSavedDataSource.getAllSavedData

    override fun getTitle(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsSavedDataSource.getSavedDataByTitle(title)
        }
    }

    override suspend fun addSaved(saved: Saved) {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsSavedDataSource.addSavedData(saved)
        }
    }

    override suspend fun deleteSaved(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsSavedDataSource.deleteSavedData(title)
        }
    }

    override suspend fun deleteSaved(saved: Saved) {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsSavedDataSource.deleteSavedData(saved)
        }
    }

    override suspend fun deleteAllSaved() {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsSavedDataSource.deleteAllSavedData()
        }
    }

    companion object {

        // 요게 머고
        private var INSTANCE: TopNewsRepositoryImpl? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = TopNewsRepositoryImpl(context)
            }
        }

        fun get(): TopNewsRepositoryImpl {
            return INSTANCE ?:
            throw IllegalStateException("TopNewsRepository must be initialized")
        }

    }


}