package com.example.presentation.savedDetail

import com.example.domain.usecase.DeleteTopNewsSavedUseCase
import com.example.presentation.presenter.MainContract

class TopNewsSavedDetailDeletePresenter constructor(
    private val deleteUseCase: DeleteTopNewsSavedUseCase
) : MainContract.TopNewsSavedDetailDeletePresenter {

    override suspend fun deleteTopNewsSaved(title: String) {
        deleteUseCase(title)
    }

}