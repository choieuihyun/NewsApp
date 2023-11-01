package com.example.presentation.presenter

import android.content.SharedPreferences
import com.example.domain.usecase.SharedPreferenceUseCase

class SharedPreferencePresenter(
    private val sharedPreferenceUseCase: SharedPreferenceUseCase
) : MainContract.TopNewsSharedPreference {

    override suspend fun getSharedPreference(): SharedPreferences {

        return sharedPreferenceUseCase()

    }
}