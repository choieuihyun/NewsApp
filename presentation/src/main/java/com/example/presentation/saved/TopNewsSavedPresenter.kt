package com.example.presentation.saved

import com.example.domain.model.SavedModel
import com.example.domain.usecase.GetTopNewsSavedUseCase
import com.example.presentation.presenter.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsSavedPresenter constructor(
    private var view: MainContract.TopNewsView<SavedModel>,
    private val getTopNewsSavedUseCase: GetTopNewsSavedUseCase
) : MainContract.TopNewsSavedPresenter {

    override suspend fun getTopNewsSaved() {

        CoroutineScope(Dispatchers.IO).launch {

            getTopNewsSavedUseCase()

            view.updateNews(getTopNewsSavedUseCase.getTopNewsSaved())

        }

    }



}