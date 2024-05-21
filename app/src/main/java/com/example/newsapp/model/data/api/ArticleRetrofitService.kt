package com.example.newsapp.model.data.api

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