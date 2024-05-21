package com.example.newsapp.model.di

import com.example.newsapp.model.data.api.ArticleRetrofitClient
import com.example.newsapp.model.data.db.ArticleDatabase
import com.example.newsapp.model.repository.ArticleRepository
import com.example.newsapp.viewmodel.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val articleModule : Module = module {

    single { ArticleRetrofitClient.apiService }
    single { ArticleDatabase.getInstance(get()).articleDao() }
    factory { ArticleRepository(get(), get()) }
    viewModel() { ArticleViewModel(get()) }

}