package com.example.presentation.topNewsDetail

import com.example.domain.model.SavedModel
import com.example.domain.usecase.AddTopNewsSavedUseCase
import com.example.presentation.presenter.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TopNewsDetailAddPresenter constructor(
    private val addUseCase: AddTopNewsSavedUseCase,
) : MainContract.TopNewsDetailAddPresenter {

    override suspend fun addTopNews(saved: SavedModel) {

        CoroutineScope(Dispatchers.IO).launch {

            addUseCase(saved)

        }

    }

}