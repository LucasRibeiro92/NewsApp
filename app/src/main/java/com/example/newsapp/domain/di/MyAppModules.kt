package com.example.newsapp.domain.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import androidx.room.Room
import com.example.newsapp.domain.data.db.ArticleDatabase
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.domain.viewmodel.ArticleViewModel

val MyAppModules: Module = module {
    single { Room.databaseBuilder(get(), ArticleDatabase::class.java, "news-db").build() }
    single { get<ArticleDatabase>().articleDao() }
    single { ArticleRepository(get(), get()) }
    viewModel() { ArticleViewModel(get()) }
}