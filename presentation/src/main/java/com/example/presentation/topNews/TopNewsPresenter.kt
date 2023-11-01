package com.example.presentation.topNews

import android.util.Log
import com.example.domain.model.ArticleModel
import com.example.domain.usecase.GetTopNewsUseCase
import com.example.presentation.presenter.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsPresenter constructor(
    private var view: MainContract.TopNewsView<ArticleModel>,
    private val useCase: GetTopNewsUseCase
) : MainContract.TopNewsPresenter {

    override suspend fun getTopNews() {

        CoroutineScope(Dispatchers.Main).launch {

            useCase()

            view.updateNews(useCase.getTopNews())

            Log.d("TopNewsPresenter", useCase.getTopNews().toString())

        }
    }





}