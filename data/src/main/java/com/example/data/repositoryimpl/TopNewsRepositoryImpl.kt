package com.example.data.repositoryimpl

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.data.datasource.local.SavedDataSource
import com.example.data.datasource.remote.TopNewsDataSource

import com.example.data.mapper.toEntity
import com.example.data.mapper.toModel
import com.example.domain.model.ArticleModel
import com.example.domain.model.SavedModel
import com.example.domain.repository.TopNewsRepository
import kotlinx.coroutines.*
import java.lang.IllegalStateException
import kotlin.coroutines.resume

// const val apiKey = BuildConfig.NEWS_API_KEY
class TopNewsRepositoryImpl constructor(
    context: Context
) : TopNewsRepository {

    // Architecture Guide: https://developer.android.com/jetpack/guide#connect-viewmodel-repository

    // Retrofit
    private val topNewsDataSource = TopNewsDataSource(context)

    // Room DB
    private val savedDataSource = SavedDataSource(context)

    // sharedPreference
    private val sharedPreferences = context.getSharedPreferences("topNewsDetail", 0)

    private var _topNews: List<ArticleModel?>? = mutableListOf()
    override val topNews: List<ArticleModel?>? // 이거 presentation layer에서 어떻게 쓸꺼임?
        get() = _topNews

    private var _topNewsCategory: List<ArticleModel?>? = mutableListOf()
    override val topNewsCategory: List<ArticleModel?>?
        get() = _topNewsCategory

    private var _topNewsSaved: List<SavedModel>? = mutableListOf()
    override val topNewsSaved: List<SavedModel>?
        get() = _topNewsSaved

    // 여기서 코루틴이 사용되는데 return값이 있을 경우 순서 맞추기가 까다로워져서
    // 데이터를 담은 변수를 전역으로 선언해 사용하며 데이터를 유지하도록 구현함.
    override suspend fun getTopNews() = suspendCancellableCoroutine { continutaion -> // MVP 패턴에서 liveData는 안쓰는게 낫다는데?

        CoroutineScope(Dispatchers.IO).launch {

            val response = topNewsDataSource.getTopNewsData()

            if(response.isSuccessful) {
                val result = response.body()?.articles
                _topNews = result?.map {
                    it?.toModel()
                }
                Log.d("TopNewsRepoImpl", topNews.toString())
            } else
                Log.d("updateNews", response.toString())


            continutaion.resume(Unit)
        }

    }

    override suspend fun getTopNewsCategory(category: String) = suspendCancellableCoroutine { continuation ->

        CoroutineScope(Dispatchers.IO).launch {

            val response = topNewsDataSource.getTopNewsCategory("2ceb3f8f8c004391b663a3fdcf806c3a", category)

            if (response.isSuccessful) {
                val result = response.body()?.articles
                    _topNewsCategory = result?.map {
                        it?.toModel()
                    }

                }

            continuation.resume(Unit)
            }
    }

    // Saved

    // 여기 일단 킵
    override fun getTopNewsSavedList() {
        _topNewsSaved = savedDataSource.getTopNewsSavedList().map {
            it.toModel()
        }

    }

    // 여기도 일단 킵
    override fun getTopNewsSaved(title: String) {
        savedDataSource.getTopNewsSaved(title)
    }

    override suspend fun addSaved(saved: SavedModel) {
        savedDataSource.addSaved(saved.toEntity())
    }

    override suspend fun deleteSaved(title: String) {
        savedDataSource.deleteSaved(title)
    }

    override suspend fun deleteSaved(saved: SavedModel) {
        savedDataSource.deleteSaved(saved.toEntity())
    }

    override suspend fun deleteAllSaved() {
        savedDataSource.deleteAllSaved()
    }

    // SharedPreference
    override suspend fun getSharedPreference(): SharedPreferences {
        return sharedPreferences
    }

    companion object {

        private var INSTANCE: TopNewsRepositoryImpl? = null

        fun initialize(context: Context) {
            if(INSTANCE == null)
                INSTANCE = TopNewsRepositoryImpl(context)
        }

        fun getInstance(): TopNewsRepositoryImpl {
            return INSTANCE ?: throw IllegalStateException("TopNewsRepository must be initialized")
        }

    }



}