package com.example.data.mapper

import com.example.data.db.remote.response.Article
import com.example.data.db.remote.response.Source
import com.example.domain.model.ArticleModel
import com.example.domain.model.SourceModel


fun Article.toModel() = ArticleModel(

    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    source = source?.toModel(),
    title = title,
    url = url,
    urlToImage = urlToImage

)

fun Source.toModel() = SourceModel(

    id = id,
    name = name

)