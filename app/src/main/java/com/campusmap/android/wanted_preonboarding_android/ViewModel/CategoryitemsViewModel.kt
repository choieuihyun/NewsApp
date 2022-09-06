package com.campusmap.android.wanted_preonboarding_android.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.campusmap.android.wanted_preonboarding_android.news.Article
import com.campusmap.android.wanted_preonboarding_android.repository.TopNewsRepository

class CategoryitemsViewModel : ViewModel() {

    private var topNewsRepository: TopNewsRepository = TopNewsRepository.get()
    private var topNewsResponseLiveData: LiveData<List<Article?>> = topNewsRepository.getTopNewsResponseLiveData()
    var topNewsCategoryItemPosition: Int = 0
    var topNewsCategoryItem: String = ""

    fun getCategoryItemData(context: Context, category: String) {
        topNewsRepository.getCategoryItemData(context, category)
    }

    fun getTopNewsItemData(context: Context) {
        topNewsRepository.getTopNewsItemData(context)
    }

    fun loadTopNewsCategoryItem(position : Int, category: String) {
        topNewsCategoryItemPosition = position
        topNewsCategoryItem = category
    }

    fun getTopNewsResponseLiveData(): LiveData<List<Article?>> {
        return topNewsResponseLiveData
    }


}