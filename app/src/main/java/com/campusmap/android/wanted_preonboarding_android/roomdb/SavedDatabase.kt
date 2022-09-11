package com.campusmap.android.wanted_preonboarding_android.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

// 필요하면 typeConverter까지
@Database(entities = [Saved::class], version = 3)
abstract class SavedDatabase : RoomDatabase() {

    abstract fun savedDao(): SavedDao

}