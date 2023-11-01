package com.example.data.mapper

import com.example.data.db.local.entity.Saved
import com.example.domain.model.SavedModel

fun SavedModel.toEntity() = Saved(

    id = id,
    author = author,
    content = content,
    publishedAt = publishedAt,
    title = title,
    urlToImage = urlToImage,
    savedButton = savedButton

)

fun Saved.toModel() = SavedModel(

    id = id,
    author = author,
    content = content,
    publishedAt = publishedAt,
    title = title,
    urlToImage = urlToImage,
    savedButton = savedButton

)