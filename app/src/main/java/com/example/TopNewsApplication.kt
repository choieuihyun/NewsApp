package com.example

import android.app.Application
import com.example.data.repositoryimpl.TopNewsRepositoryImpl

class TopNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        TopNewsRepositoryImpl.initialize(this)
    }
}