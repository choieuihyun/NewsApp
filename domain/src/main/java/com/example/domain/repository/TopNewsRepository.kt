package com.example.domain.repository

import android.content.SharedPreferences
import com.example.domain.model.ArticleModel
import com.example.domain.model.SavedModel

interface TopNewsRepository {

    // 코드 몇개 지워야함
    // 아 맞다 가져오기만 하는건 suspend 안해도 되고 add , delete같은 수정 작업만 했어야했지
    // hilt 사용해서 싱글톤으로 만든게 아니라 dietApp이랑 다르게?

    val topNews: List<ArticleModel?>? // 이거 presentation layer에서 어떻게 쓸꺼임?

    val topNewsCategory: List<ArticleModel?>?

    val topNewsSaved: List<SavedModel?>?

    // TopNews

    suspend fun getTopNews()

    // TopNewsCategory

    suspend fun getTopNewsCategory(category: String)

    // Saved

    fun getTopNewsSavedList()

    fun getTopNewsSaved(title: String)

    suspend fun addSaved(saved: SavedModel)

    suspend fun deleteSaved(title: String)

    suspend fun deleteSaved(saved: SavedModel)

    suspend fun deleteAllSaved()

    // SharedPreference

    suspend fun getSharedPreference(): SharedPreferences


}