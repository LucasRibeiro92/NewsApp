package com.example.newsapp.domain.data.api

import com.example.newsapp.domain.data.db.ArticleEntity

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleEntity>
)
