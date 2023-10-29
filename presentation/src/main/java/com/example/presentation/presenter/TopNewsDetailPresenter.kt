package com.example.presentation.presenter

import com.example.domain.model.SavedModel
import com.example.domain.usecase.AddTopNewsSavedUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TopNewsDetailPresenter constructor(
    private val useCase: AddTopNewsSavedUseCase
) : MainContract.TopNewsPresenter.TopNewsDetailPresenter {

    override suspend fun addTopNews(saved: SavedModel) {

        CoroutineScope(Dispatchers.IO).launch {

            useCase(saved)

        }

    }

}