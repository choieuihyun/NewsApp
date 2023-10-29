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

        interface TopNewsDetailPresenter {

            suspend fun addTopNews(saved: SavedModel)

        }
    }

    interface TopNewsCategoryPresenter {
        suspend fun getTopNewsCategory(category: String)
        suspend fun savedTopNewsCategory()
    }

    interface TopNewsSavedPresenter {
        suspend fun getTopNewsSaved()
        suspend fun addTopNewsSaved(saved: SavedModel)

        suspend fun deleteTopNewsSaved(title: String)
    }

}