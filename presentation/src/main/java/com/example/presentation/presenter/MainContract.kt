package com.example.presentation.presenter

import android.content.Context
import com.example.domain.model.ArticleModel
import com.example.domain.model.SavedModel

interface MainContract {

    interface TopNewsView { // view에서 ui update를 하기위한 method 선언
        fun updateNews(topNews: List<ArticleModel?>?) // 모르겠다
    }

    interface TopNewsPresenter { // business logic 선언
        suspend fun getTopNews(context: Context)
        suspend fun savedTopNews(saved: SavedModel)
    }


    interface Saved { // model에서 presenter를 참조 하고 있지 않기 때문에 Saved interface안에 interface를 생성해서 presenter에서 상속받도록 함.

    }

    interface TopNewsCategoryPresenter {
        suspend fun getTopNewsCategory()
        suspend fun savedTopNewsCategory()
    }

    interface GetTopNewsSavedPresenter {
        suspend fun getTopNewsSaved(saved: SavedModel)
        suspend fun savedTopNews(saved: SavedModel)
    }

    interface TopNewsSavedPresenter {
        fun getTopNewsSaved()
        suspend fun deleteTopNewsSaved()
    }

}