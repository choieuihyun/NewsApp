package com.campusmap.android.wanted_preonboarding_android.repository

import android.app.Application
import android.content.SharedPreferences

class TopNewsApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        TopNewsRepository.initialize(this)
        val sharedPreferences = getSharedPreferences("", 0)
    }
}