package com.example.data.datasource.local

import android.content.Context
import androidx.room.Room
import com.example.data.db.local.db.SavedDatabase
import com.example.data.db.local.entity.Saved
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SavedDataSource constructor(context: Context){

    private val database: SavedDatabase = Room.databaseBuilder(
        context.applicationContext,
        SavedDatabase::class.java,
        "savedDB").fallbackToDestructiveMigration()
        .build()

    private val savedDao = database.savedDao()


    // 데이터를 불러오는 것과 첨삭의 차이인가?
    // fun getAllSavedLiveData() = savedDao.getAllSavedNewsLiveData()

    fun getTopNewsSavedList() : List<Saved> {
        return savedDao.getTopNewsSavedList()
    }

    fun getTopNewsSaved(title: String) = savedDao.getTopNewsSaved(title)

    suspend fun addSaved(saved: Saved) =
        withContext(Dispatchers.IO) {
            savedDao.addSaved(saved)
        }

    suspend fun deleteSaved(title: String) =
        withContext(Dispatchers.IO) {
            savedDao.deleteSaved(title)
        }

    suspend fun deleteSaved(saved: Saved) =
        withContext(Dispatchers.IO) {
            savedDao.deleteSaved(saved)
        }

    suspend fun deleteAllSaved() =
        withContext(Dispatchers.IO) {
            savedDao.deleteAllSaved()
        }


}