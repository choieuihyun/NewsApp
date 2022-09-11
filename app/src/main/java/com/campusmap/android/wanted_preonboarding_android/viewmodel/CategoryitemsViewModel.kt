package com.campusmap.android.wanted_preonboarding_android.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.campusmap.android.wanted_preonboarding_android.news.Article
import com.campusmap.android.wanted_preonboarding_android.repository.TopNewsRepository

class CategoryitemsViewModel : ViewModel() {

    private var topNewsRepository: TopNewsRepository = TopNewsRepository.get()
    private var topNewsCategoryResponseLiveData: LiveData<List<Article?>> = topNewsRepository.getTopNewsCategoryAllResponseLiveData()

    var topNewsCategoryItemPosition: Int = 0
    var topNewsCategoryItem: String = ""

    fun getCategoryItemData(context: Context, category: String) {
        topNewsRepository.getCategoryItemData(context, category)
    }

/*    fun getTopNewsItemData() {
        topNewsRepository.getTopNewsItemData()
    }*/

    fun loadTopNewsCategoryItem(position : Int, category: String) {
        topNewsCategoryItemPosition = position
        topNewsCategoryItem = category
    }

    fun getTopNewsCategoryResponseLiveData(): LiveData<List<Article?>> {
        return topNewsCategoryResponseLiveData
    }


}