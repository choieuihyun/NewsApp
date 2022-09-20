package com.campusmap.android.wanted_preonboarding_android.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Saved (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null, // 요렇게하고 null로 주면 autoGenerate
    var author: String?,
    var content: String?,
    var publishedAt: String?,
    var title: String?,
    var urlToImage: String?,
    var savedButton: Boolean?
) {

}