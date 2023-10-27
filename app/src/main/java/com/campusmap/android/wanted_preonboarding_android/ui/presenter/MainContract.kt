package com.campusmap.android.wanted_preonboarding_android.ui.presenter

import com.campusmap.android.wanted_preonboarding_android.data.db.local.entity.Saved
import com.campusmap.android.wanted_preonboarding_android.data.db.remote.response.Article
import com.campusmap.android.wanted_preonboarding_android.ui.topnews.TopNews

interface MainContract {

    interface TopNewsView { // view에서 ui update를 하기위한 method 선언
        fun updateNews(topNews: List<Article?>) // 모르겠다
    }

    interface TopNewsPresenter { // business logic 선언
        suspend fun getTopNews(): List<Article?>?
//        suspend fun getTopNewsAllLiveData()
//        suspend fun getTopNewsAllData()
        suspend fun savedTopNews(saved: Saved)
    }

    interface TopNewsCategoryPresenter {
        suspend fun getTopNewsCategory()
        suspend fun savedTopNewsCategory()
    }

    interface GetTopNewsSavedPresenter {
        suspend fun getTopNewsSaved(saved: Saved)
        suspend fun savedTopNews(saved: Saved)
    }

    interface TopNewsSavedPresenter {
        fun getTopNewsSaved()
        suspend fun deleteTopNewsSaved()
    }

}