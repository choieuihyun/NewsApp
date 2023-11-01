package com.example.domain.usecase

import android.content.SharedPreferences
import com.example.domain.repository.TopNewsRepository

class SharedPreferenceUseCase constructor(
    private val repository: TopNewsRepository
) {

    suspend operator fun invoke(): SharedPreferences{
        return repository.getSharedPreference()
    }

}