package com.campusmap.android.wanted_preonboarding_android.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campusmap.android.wanted_preonboarding_android.news.Article
import com.campusmap.android.wanted_preonboarding_android.repository.TopNewsRepository

class TopNewsViewModel : ViewModel() {

    private var topNewsRepository: TopNewsRepository = TopNewsRepository.get()
    private var topNewsResponseLiveData: LiveData<List<Article?>> = topNewsRepository.getTopNewsResponseLiveData()
    var topNewsPosition: Int = 0

    fun getTopNewsItemData(context: Context) {
        topNewsRepository.getTopNewsItemData(context)
    }

    fun loadTopNewsItem(position : Int) {
        topNewsPosition = position
    }

    fun getTopNewsResponseLiveData(): LiveData<List<Article?>>{
        return topNewsResponseLiveData
    }


}