package com.example.data.db.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.data.db.local.entity.Saved


@Dao
interface SavedDao {

    //saved
        @Query("SELECT * FROM saved")
            fun getAllSavedNewsLiveData() : LiveData<List<Saved>>

        @Query("SELECT * FROM saved")
            fun getTopNewsSavedList() : List<Saved>

        @Query("SELECT title FROM saved WHERE title = :title")
            fun getTopNewsSaved(title: String) : String

// https://developer.android.com/training/data-storage/room/accessing-data?hl=ko

        @Insert
            fun addSaved(saved: Saved)

        @Query("DELETE FROM saved WHERE title = :title")
            fun deleteSaved(title: String)

        @Delete
            fun deleteSaved(saved: Saved)

        @Query("DELETE FROM saved")
            fun deleteAllSaved()


}