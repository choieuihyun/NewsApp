package com.campusmap.android.wanted_preonboarding_android.domain.usecase

import com.campusmap.android.wanted_preonboarding_android.domain.repository.TopNewsRepository

class GetAllTopNewsUseCase(
    private val repository: TopNewsRepository
) {

    operator fun invoke() {
        repository.getTopNewsAllResponseLiveData()
    }
}