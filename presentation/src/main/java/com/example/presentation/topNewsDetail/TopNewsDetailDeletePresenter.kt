package com.example.presentation.topNewsDetail

import com.example.domain.usecase.DeleteTopNewsSavedUseCase
import com.example.presentation.presenter.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TopNewsDetailDeletePresenter constructor(
    private val deleteTopNewsSavedUseCase: DeleteTopNewsSavedUseCase
) : MainContract.TopNewsDetailDeletePresenter {

    override suspend fun deleteTopNews(title: String) {

        CoroutineScope(Dispatchers.IO).launch {

            deleteTopNewsSavedUseCase(title)

        }

    }

}