package com.example.presentation.categoriesDetail

import com.example.domain.model.SavedModel
import com.example.domain.usecase.AddTopNewsSavedUseCase
import com.example.presentation.presenter.MainContract

class TopNewsCategoryDetailAddPresenter constructor(
    private val addUseCase: AddTopNewsSavedUseCase
) : MainContract.TopNewsCategoryDetailAddPresenter {

    override suspend fun addTopNewsCategory(saved: SavedModel) {
        addUseCase(saved)
    }

}