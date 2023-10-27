package com.campusmap.android.wanted_preonboarding_android.data.datasource.local

import android.content.Context
import androidx.room.Room
import com.campusmap.android.wanted_preonboarding_android.data.db.local.db.SavedDatabase
import com.campusmap.android.wanted_preonboarding_android.data.db.local.entity.Saved

class TopNewsSavedDataSource constructor(context: Context){

    private val database: SavedDatabase = Room.databaseBuilder(
        context.applicationContext,
        SavedDatabase::class.java,
        "savedDB").fallbackToDestructiveMigration()
        .build()

    private val savedDao = database.savedDao()

    val getAllSavedLiveData = savedDao.getAllSavedNewsLiveData()

    val getAllSavedData = savedDao.getAllSavedNewsData()

    fun getSavedDataByTitle(title: String) = savedDao.getSavedNewsData(title)

    fun addSavedData(saved: Saved) = savedDao.addSaved(saved)

    fun deleteSavedData(title: String) = savedDao.deleteSaved(title)

    fun deleteSavedData(saved: Saved) = savedDao.deleteSaved(saved)

    fun deleteAllSavedData() = savedDao.deleteAllSaved()

}