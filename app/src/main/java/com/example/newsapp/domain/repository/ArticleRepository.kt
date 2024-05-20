package com.example.newsapp.domain.repository

import com.example.newsapp.domain.data.api.ArticleRetrofitService
import com.example.newsapp.domain.data.db.ArticleDao
import com.example.newsapp.domain.data.db.ArticleEntity
import com.example.newsapp.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository(
    private val apiService: ArticleRetrofitService,
    private val articleDao: ArticleDao
) {

    suspend fun fetchNewsArticles(onSuccess: (List<ArticleEntity>) -> Unit, onError: (Exception) -> Unit) {
        withContext(Dispatchers.IO) {
            val call = apiService.getTopHeadlines("br", API_KEY)
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    onSuccess(articles)
                } else {
                    onError(Exception("Error: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    /*
    suspend fun getAllFavorites(): List<ArticleEntity> {
        return withContext(Dispatchers.IO) {
            articleDao.getAllFavorites()
        }
    }
    */
    suspend fun addFavorite(article: ArticleEntity) {
        withContext(Dispatchers.IO) {
            articleDao.insert(article)
        }
    }

    suspend fun removeFavorite(url: String) {
        withContext(Dispatchers.IO) {
            articleDao.deleteByUrl(url)
        }
    }
}

