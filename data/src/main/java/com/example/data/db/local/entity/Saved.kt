package com.example.data.db.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Saved (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var author: String?,
    var content: String?,
    var publishedAt: String?,
    var title: String?,
    var urlToImage: String?,
    var savedButton: Boolean?
) {

}