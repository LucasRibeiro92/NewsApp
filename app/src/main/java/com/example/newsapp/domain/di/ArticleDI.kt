package com.example.newsapp.domain.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.domain.data.db.ArticleDatabase
import com.example.newsapp.utils.Constants

fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, ArticleDatabase::class.java, Constants.ARTICLE_DATABASE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(db: ArticleDatabase) = db.articleDao()