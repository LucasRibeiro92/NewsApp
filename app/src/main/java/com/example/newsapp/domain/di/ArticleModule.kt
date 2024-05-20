package com.example.newsapp.domain.di

import com.example.newsapp.domain.data.api.ArticleRetrofitClient
import com.example.newsapp.domain.data.db.ArticleDatabase
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.domain.viewmodel.ArticleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val articleModule : Module = module {

    single { ArticleRetrofitClient.apiService }
    single { ArticleDatabase.getInstance(get()).articleDao() }
    factory { ArticleRepository(get(), get()) }
    viewModel() { ArticleViewModel(get()) }

}