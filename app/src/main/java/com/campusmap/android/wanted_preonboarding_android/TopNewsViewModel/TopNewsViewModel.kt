package com.campusmap.android.wanted_preonboarding_android.TopNewsViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.campusmap.android.wanted_preonboarding_android.news.Article
import com.campusmap.android.wanted_preonboarding_android.repository.TopNewsRepository

class TopNewsViewModel : ViewModel() {

    private var topNewsRepository: TopNewsRepository = TopNewsRepository.get()
    private var topNewsResponseLiveData: LiveData<List<Article?>> = topNewsRepository.getTopNewsResponseLiveData()

    fun getTopNewsItemData(context: Context) {
        topNewsRepository.getTopNewsItemData(context)
    }

    fun getCategoryItemData(context: Context, category: String) {
        topNewsRepository.getCategoryItemData(context, category)
    }

    fun getTopNewsResponseLiveData(): LiveData<List<Article?>>{
        return topNewsResponseLiveData
    }


}