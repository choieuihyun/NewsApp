package com.example.domain.usecase

import com.example.domain.model.ArticleModel
import com.example.domain.repository.TopNewsRepository

class GetTopNewsCategoryUseCase constructor(
    private val repository: TopNewsRepository
) {

    suspend operator fun invoke(category: String) {
        repository.getTopNewsCategory(category)
    }

    fun getTopNewsCategory() : List<ArticleModel?>? {
        return repository.topNewsCategory
    }

}