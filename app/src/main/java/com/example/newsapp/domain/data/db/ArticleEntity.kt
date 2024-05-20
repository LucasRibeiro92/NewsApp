package com.example.newsapp.domain.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.utils.Constants.ARTICLE_TABLE

@Entity(tableName = ARTICLE_TABLE)
data class ArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String?,
    val author: String?,
    val publishedAt: String?,
    val sourceName: String?
)
