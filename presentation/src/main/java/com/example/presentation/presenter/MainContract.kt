package com.example.presentation.presenter

import android.content.Context
import com.example.domain.model.ArticleModel
import com.example.domain.model.SavedModel

interface MainContract {

    interface TopNewsView<T> { // view에서 ui update를 하기위한 method 선언

        fun updateNews(topNews: List<T?>?)

    }

    interface TopNewsPresenter { // business logic 선언
        
        suspend fun getTopNews()


    }

    interface TopNewsDetailAddPresenter { // TopNewsPresenter 내부에서 분리.

            suspend fun addTopNews(saved: SavedModel)

    }

    interface TopNewsDetailDeletePresenter {

        suspend fun deleteTopNews(title: String)

    }


    interface TopNewsCategoryPresenter {
        suspend fun getTopNewsCategory(category: String)

    }

    interface TopNewsCategoryDetailAddPresenter {

        suspend fun addTopNewsCategory(saved: SavedModel)
    }

    interface TopNewsCategoryDetailDeletePresenter {

        suspend fun deleteTopNewsCategory(title: String)

    }


    // 즐겨찾기
    interface TopNewsSavedPresenter {

        suspend fun getTopNewsSaved()

    }

    interface TopNewsSavedDetailAddPresenter {
        suspend fun addTopNewsSaved(saved: SavedModel)

    }

    interface TopNewsSavedDetailDeletePresenter {

        suspend fun deleteTopNewsSaved(title: String)

    }

}