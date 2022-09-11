package com.campusmap.android.wanted_preonboarding_android.viewmodel

import androidx.lifecycle.ViewModel
import com.campusmap.android.wanted_preonboarding_android.repository.TopNewsRepository
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved

class SavedViewModel : ViewModel() {

    private var savedRepository: TopNewsRepository = TopNewsRepository.get()

/*    fun addSaved(saved: Saved) {
        savedRepository.addSaved(saved)
    }

    fun deleteSaved(saved: Saved) {
        savedRepository.deleteSaved(saved)
    }*/

}