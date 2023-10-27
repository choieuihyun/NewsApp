package com.campusmap.android.wanted_preonboarding_android.domain.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.campusmap.android.wanted_preonboarding_android.data.db.local.entity.Saved
import com.campusmap.android.wanted_preonboarding_android.data.db.remote.response.Article

interface TopNewsRepository {

    // 코드 몇개 지워야함
    // 아 맞다 가져오기만 하는건 suspend 안해도 되고 add , delete같은 수정 작업만 했어야했지

    // TopNews

    suspend fun getTopNewsResponseData()

    suspend fun getTopNewsLiveData(): LiveData<List<Article?>>

    suspend fun getTopNewsAllResponseData(): List<Article?>?


    // TopNewsCategory

    suspend fun getCategoryResponseData(category: String)

    suspend fun getTopNewsCategoryAllResponseLiveData(): LiveData<List<Article?>>

    suspend fun getTopNewsCategoryAllResponseData(): List<Article?>?


    // Saved

    fun getAllSavedLiveData(): LiveData<List<Saved>>

    fun getAllSavedData(): List<Saved>

    fun getTitle(title: String)

    suspend fun addSaved(saved: Saved)

    suspend fun deleteSaved(title: String)

    suspend fun deleteSaved(saved: Saved)

    suspend fun deleteAllSaved()

}