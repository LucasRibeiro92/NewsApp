package com.example.newsapp.model.repository

import com.example.newsapp.model.data.api.ArticleRetrofitService
import com.example.newsapp.model.data.db.ArticleDao
import com.example.newsapp.model.data.db.ArticleEntity
import com.example.newsapp.utils.AppError
import com.example.newsapp.utils.Constants.API_KEY
import com.example.newsapp.utils.Constants.API_LOCALE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository(
    private val apiService: ArticleRetrofitService,
    private val articleDao: ArticleDao
) {


    suspend fun fetchNewsArticles(onSuccess: (List<ArticleEntity>) -> Unit, onError: (AppError) -> Unit) {
        withContext(Dispatchers.IO) {
            val call = apiService.getTopHeadlines(API_LOCALE, API_KEY)
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    onSuccess(articles)
                } else {
                    onError(AppError.NetworkError("Error: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                onError(AppError.UnknownError("An unknown error occurred: ${e.message}"))
            }
        }
    }


    suspend fun toggleFavorite(article: ArticleEntity) {
        withContext(Dispatchers.IO) {
            try {
                val existingArticle = articleDao.getArticleByUrl(article.url)
                if (existingArticle != null) {
                    articleDao.deleteByUrl(article.url)
                } else {
                    articleDao.insert(article)
                }
            } catch (e: Exception) {
                throw AppError.DatabaseError("Database operation failed: ${e.message}")
            }
        }
    }

    suspend fun isArticleFavorite(url: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                articleDao.getArticleByUrl(url) != null
            } catch (e: Exception) {
                throw AppError.DatabaseError("Database operation failed: ${e.message}")
            }
        }
    }
}

