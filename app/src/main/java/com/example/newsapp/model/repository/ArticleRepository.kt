package com.example.newsapp.model.repository

import com.example.newsapp.model.data.api.ArticleRetrofitService
import com.example.newsapp.model.data.db.ArticleDao
import com.example.newsapp.model.data.db.ArticleEntity
import com.example.newsapp.utils.Constants.API_KEY
import com.example.newsapp.utils.Constants.API_LOCALE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository(
    private val apiService: ArticleRetrofitService,
    private val articleDao: ArticleDao
) {

    suspend fun fetchNewsArticles(onSuccess: (List<ArticleEntity>) -> Unit, onError: (Exception) -> Unit) {
        withContext(Dispatchers.IO) {
            val call = apiService.getTopHeadlines(API_LOCALE, API_KEY)
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

    suspend fun toggleFavorite(article: ArticleEntity) {
        withContext(Dispatchers.IO) {
            val existingArticle = articleDao.getArticleByUrl(article.url)
            if (existingArticle != null) {
                // Article is already a favorite, remove it from the database
                articleDao.deleteByUrl(article.url)
            } else {
                // Article is not a favorite, add it to the database
                articleDao.insert(article)
            }
        }
    }

    suspend fun isArticleFavorite(url: String): Boolean {
        return withContext(Dispatchers.IO) {
            articleDao.getArticleByUrl(url) != null
        }
    }
}

