package com.example.domain.usecase

import com.example.domain.model.SavedModel
import com.example.domain.repository.TopNewsRepository

class AddTopNewsSavedUseCase constructor(
    private val repository: TopNewsRepository
) {

    suspend operator fun invoke(saved: SavedModel) {
        repository.addSaved(saved)
    }

}