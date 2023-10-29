package com.example.domain.usecase

import android.util.Log
import com.example.domain.model.SavedModel
import com.example.domain.repository.TopNewsRepository

class GetTopNewsSavedUseCase constructor(
    private val repository: TopNewsRepository
) {

    operator fun invoke() {
        repository.getTopNewsSavedList()
    }

    fun getTopNewsSaved() : List<SavedModel?>? {
        return repository.topNewsSaved
    }

}