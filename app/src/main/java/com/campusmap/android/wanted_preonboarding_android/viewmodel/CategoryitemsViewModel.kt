package com.campusmap.android.wanted_preonboarding_android.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.campusmap.android.wanted_preonboarding_android.retrofit.Article
import com.campusmap.android.wanted_preonboarding_android.repository.TopNewsRepository
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryitemsViewModel : ViewModel() {

    private var topNewsRepository: TopNewsRepository = TopNewsRepository.get()
    private var topNewsCategoryResponseLiveData: LiveData<List<Article?>> = topNewsRepository.getTopNewsCategoryAllResponseLiveData()

    var topNewsCategoryItemPosition: Int = 0
    var topNewsCategoryItem: String = ""

    fun getCategoryItemData(context: Context, category: String) {
        topNewsRepository.getCategoryItemData(context, category)
    }


    fun setTopNewsCategoryItemInfo(position : Int, category: String) {
        topNewsCategoryItemPosition = position
        topNewsCategoryItem = category
    }

    fun getTopNewsCategoryResponseLiveData(): LiveData<List<Article?>> {
        return topNewsCategoryResponseLiveData
    }

    fun getTopNewsCategoryResponseData() : List<Article?>? {
        return topNewsRepository.getTopNewsCategoryAllResponseData()
    }

    // saved

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

}