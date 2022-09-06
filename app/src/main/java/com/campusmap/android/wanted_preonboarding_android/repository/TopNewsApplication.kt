package com.campusmap.android.wanted_preonboarding_android.repository

import android.app.Application

class TopNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        TopNewsRepository.initialize(this)
    }
}