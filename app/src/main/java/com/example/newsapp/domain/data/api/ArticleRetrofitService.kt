package com.example.newsapp.domain.data.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.newsapp.domain.data.db.ArticleEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleRetrofitService {
    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsApiResponse>
}