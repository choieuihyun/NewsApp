package com.campusmap.android.wanted_preonboarding_android.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.campusmap.android.wanted_preonboarding_android.news.Article
import retrofit2.http.DELETE


@Dao
interface SavedDao {

    //saved
        @Query("SELECT * FROM saved")
            fun getAllSavedNewsLiveData() : LiveData<List<Saved>>

        @Query("SELECT * FROM saved")
            fun getAllSavedNewsData() : List<Saved>

        @Query("SELECT title FROM saved WHERE title = :title")
            fun getSavedNewsData(title: String) : String

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