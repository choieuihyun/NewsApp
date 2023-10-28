package com.example.presentation.presenter

import android.content.Context
import android.util.Log
import com.example.domain.model.SavedModel
import com.example.domain.usecase.GetTopNewsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsPresenter constructor(
    private var view: MainContract.TopNewsView,
    private val useCase: GetTopNewsUseCase
) : MainContract.TopNewsPresenter {

    override suspend fun getTopNews() {

        CoroutineScope(Dispatchers.Main).launch {

            useCase()

            view.updateNews(useCase.getTopNews())

            Log.d("TopNewsPresenter", useCase.getTopNews().toString())

        }
    }

    override suspend fun savedTopNews(saved: SavedModel) {
        TODO("Not yet implemented")
    }




}