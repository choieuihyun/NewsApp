package com.campusmap.android.wanted_preonboarding_android.ui.presenter

import com.campusmap.android.wanted_preonboarding_android.data.db.local.entity.Saved
import com.campusmap.android.wanted_preonboarding_android.data.db.remote.response.Article
import com.campusmap.android.wanted_preonboarding_android.data.repositoryimpl.TopNewsRepositoryImpl
import com.campusmap.android.wanted_preonboarding_android.domain.repository.TopNewsRepository

class TopNewsPresenter constructor(
    private var view: MainContract.TopNewsView
    // private val repository: TopNewsRepository 아니 원래 이렇게해서 하려했는데 어떻게 해야하는지 모르겠다 fragment에서 repository를 못만들겠음.
    // repository가 인터페이스여서..
) : MainContract.TopNewsPresenter{


    override suspend fun getTopNews(): List<Article?>? {

        val repository = TopNewsRepositoryImpl.get() // 그래서 원래 이것도 아닌데 모르겠다

        val topNews = repository.getTopNewsAllResponseData()

        if (topNews != null) {
            view.updateNews(topNews)
        }

        return topNews
    }




    override suspend fun savedTopNews(saved: Saved) {
        TODO("Not yet implemented")
    }




}