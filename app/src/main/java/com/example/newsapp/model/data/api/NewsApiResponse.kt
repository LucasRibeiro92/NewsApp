package com.example.newsapp.model.data.api

import com.example.newsapp.model.data.db.ArticleEntity

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleEntity>
)
