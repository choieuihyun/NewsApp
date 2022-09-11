package com.campusmap.android.wanted_preonboarding_android.viewmodel

import androidx.lifecycle.ViewModel
import com.campusmap.android.wanted_preonboarding_android.repository.TopNewsRepository
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved

class SavedViewModel : ViewModel() {

    private var savedRepository: TopNewsRepository = TopNewsRepository.get()

    var savedNewsPosition = 0

    val savedNewsLiveData = savedRepository.getAllSavedLiveData()

    suspend fun savedNewsData() = savedRepository.getAllSavedData()

    fun setTopNewsItemPosition(position : Int) {
        savedNewsPosition = position
    }

    fun addSaved(saved: Saved) {
        savedRepository.addSaved(saved)
    }

    fun deleteSaved(saved: Saved) {
        savedRepository.deleteSaved(saved)
    }

    fun deleteSaved(title: String) {
        savedRepository.deleteSaved(title)
    }

}