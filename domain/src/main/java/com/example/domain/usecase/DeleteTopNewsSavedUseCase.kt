package com.example.domain.usecase

import com.example.domain.repository.TopNewsRepository

class DeleteTopNewsSavedUseCase constructor(
    private val repository: TopNewsRepository
) {

    suspend operator fun invoke(title: String) {

        repository.deleteSaved(title)

    }
}