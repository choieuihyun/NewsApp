package com.example.presentation.presenter

import android.content.Context
import com.example.domain.model.ArticleModel
import com.example.domain.model.SavedModel

interface MainContract {

    interface TopNewsView<T> { // view에서 ui update를 하기위한 method 선언
        fun updateNews(topNews: List<T?>?)
        //fun updateNews(topNews: List<T?>?)
    }

    interface TopNewsPresenter { // business logic 선언
        suspend fun getTopNews()
        suspend fun savedTopNews(saved: SavedModel)
    }

    interface TopNewsCategoryPresenter {
        suspend fun getTopNewsCategory(category: String)
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