package com.example.presentation.categories

import com.example.domain.model.ArticleModel
import com.example.domain.usecase.GetTopNewsCategoryUseCase
import com.example.presentation.presenter.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsCategoryPresenter constructor(
    private var view: MainContract.TopNewsView<ArticleModel>,
    private val useCase: GetTopNewsCategoryUseCase
) : MainContract.TopNewsCategoryPresenter {

    override suspend fun getTopNewsCategory(category: String) {

        CoroutineScope(Dispatchers.Main).launch {

            useCase(category)

            view.updateNews(useCase.getTopNewsCategory())

        }

    }


}