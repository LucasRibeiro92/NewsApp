package com.example.newsapp.domain.data.api

import com.example.newsapp.utils.Constants.API_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleRetrofitClient {
    companion object {
        val apiService: ArticleRetrofitService by lazy {
            Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ArticleRetrofitService::class.java)
        }
    }
}