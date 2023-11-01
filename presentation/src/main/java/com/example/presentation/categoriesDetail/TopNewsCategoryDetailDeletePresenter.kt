package com.example.presentation.categoriesDetail

import com.example.domain.usecase.DeleteTopNewsSavedUseCase
import com.example.presentation.presenter.MainContract

class TopNewsCategoryDetailDeletePresenter constructor(
    private val deleteUseCase: DeleteTopNewsSavedUseCase
) : MainContract.TopNewsCategoryDetailDeletePresenter {

    override suspend fun deleteTopNewsCategory(title: String) {
        deleteUseCase(title)
    }

}