package com.example.newsapp.domain.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.newsapp.domain.data.db.ArticleDao
import com.example.newsapp.domain.data.db.ArticleEntity
import com.example.newsapp.utils.Constants.API_BASE_URL
import com.example.newsapp.utils.Constants.API_KEY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository(private val context: Context, private val articleDao: ArticleDao) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    suspend fun fetchArticles() {
        withContext(Dispatchers.IO) {
            val url = "$API_BASE_URL?country=us&apiKey=$API_KEY"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val articlesJson = response.getJSONArray("articles").toString()
                    val listType = object : TypeToken<List<ArticleEntity>>() {}.type
                    val articles: List<ArticleEntity> = Gson().fromJson(articlesJson, listType)
                    articleDao.insertAll(*articles.toTypedArray())
                },
                { error ->
                    // Handle error
                })
            requestQueue.add(jsonObjectRequest)
        }
    }

    suspend fun getSavedArticles(): List<ArticleEntity> {
        return articleDao.getAll()
    }
}
