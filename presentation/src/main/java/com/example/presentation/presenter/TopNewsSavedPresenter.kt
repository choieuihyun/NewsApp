package com.example.presentation.presenter

import android.util.Log
import com.example.domain.model.SavedModel
import com.example.domain.usecase.GetTopNewsSavedUseCase
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

    override suspend fun addTopNewsSaved(saved: SavedModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTopNewsSaved(title: String) {
        TODO("Not yet implemented")
    }


}