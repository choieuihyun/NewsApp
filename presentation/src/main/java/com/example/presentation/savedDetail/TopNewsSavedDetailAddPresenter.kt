package com.example.presentation.savedDetail

import com.example.domain.model.SavedModel
import com.example.domain.usecase.AddTopNewsSavedUseCase
import com.example.presentation.presenter.MainContract

class TopNewsSavedDetailAddPresenter constructor(
    private val addUseCase: AddTopNewsSavedUseCase
) : MainContract.TopNewsSavedDetailAddPresenter {

    override suspend fun addTopNewsSaved(saved: SavedModel) {
        addUseCase(saved)
    }

}