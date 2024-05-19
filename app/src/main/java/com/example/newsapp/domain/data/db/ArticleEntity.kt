package com.example.newsapp.domain.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String
)
