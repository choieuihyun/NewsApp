package com.campusmap.android.wanted_preonboarding_android.viewmodel

import androidx.lifecycle.*
import com.campusmap.android.wanted_preonboarding_android.retrofit.Article
import com.campusmap.android.wanted_preonboarding_android.repository.TopNewsRepository
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved
import kotlinx.coroutines.*


class TopNewsViewModel : ViewModel() {

    private var topNewsRepository: TopNewsRepository = TopNewsRepository.get()
    private var topNewsResponseAllLiveData: LiveData<List<Article?>> = topNewsRepository.getTopNewsAllResponseLiveData()

    var topNewsPosition: Int = 0

    // Retrofit Data

    fun getTopNewsItemData() {
        topNewsRepository.getTopNewsItemData // topNews retrofit 연결하면서 topNewsData 반환.
    }

    fun getTopNewsAllResponseLiveData(): LiveData<List<Article?>>{ // TopNews 전체 Data
        return topNewsResponseAllLiveData
    }


    suspend fun getTopNewsAllResponseData(): List<Article?>? {
        return topNewsRepository.getTopNewsAllResponseData()
    }

    fun setTopNewsItemPosition(position : Int) {
        topNewsPosition = position
    }

    // Saved Data


    fun addSaved(saved: Saved) {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsRepository.addSaved(saved)
        }
    }



    fun getTitle(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsRepository.getTitle(title)
        }
    }

    fun deleteSaved(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsRepository.deleteSaved(title)
        }
    }

    fun deleteAllSaved() {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsRepository.deleteAllSaved()
        }
    }


}