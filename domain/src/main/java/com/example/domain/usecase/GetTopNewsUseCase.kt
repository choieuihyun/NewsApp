package com.example.domain.usecase

import com.example.domain.model.ArticleModel
import com.example.domain.repository.TopNewsRepository

class GetTopNewsUseCase constructor(
    private val repository: TopNewsRepository){

    suspend operator fun invoke() {
        repository.getTopNews()
    }

    fun getTopNews() : List<ArticleModel?>? {
        return repository.topNews
    }


}
