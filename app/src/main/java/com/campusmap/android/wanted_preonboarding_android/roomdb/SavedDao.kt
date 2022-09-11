package com.campusmap.android.wanted_preonboarding_android.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.campusmap.android.wanted_preonboarding_android.news.Article


@Dao
interface SavedDao {

        @Query("SELECT * FROM saved")
            fun getAllSavedNewsLiveData() : LiveData<List<Saved>>

        @Query("SELECT title FROM saved WHERE title = :title")
            fun getSavedNewsData(title: String) : String

//        @Query("SELECT id FROM saved WHERE id = :id")
//            fun getSavedIdData(id: Int) : Int

/*    @Query("SELECT * FROM saved")
        fun getAllSavedNewsData() : List<Saved>

    @Query("SELECT * FROM saved") // 생각해보니까 id해서 몇개만 보여줄 필요가 없잖아 그냥 LiveData로 다 갱신하면 되는데.
        fun getSavedNewsData() : Saved*/

// https://developer.android.com/training/data-storage/room/accessing-data?hl=ko

        @Insert
            fun addSaved(saved: Saved)

        @Query("DELETE FROM saved WHERE title = :title")
            fun deleteSaved(title: String)

        @Query("DELETE FROM saved")
            fun deleteAllSaved()

        /*@Delete
            fun deleteSaved(saved: Saved)*/


}