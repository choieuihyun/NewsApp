package com.example.presentation.presenter

import com.example.domain.usecase.GetTopNewsCategoryUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsCategoryPresenter constructor(
    private var view: MainContract.TopNewsView,
    private val useCase: GetTopNewsCategoryUseCase
) : MainContract.TopNewsCategoryPresenter {

    override suspend fun getTopNewsCategory(category: String) {

        CoroutineScope(Dispatchers.Main).launch {

            useCase(category)

            view.updateNews(useCase.getTopNewsCategory())

        }

    }

    override suspend fun savedTopNewsCategory() {
        TODO("Not yet implemented")
    }

}