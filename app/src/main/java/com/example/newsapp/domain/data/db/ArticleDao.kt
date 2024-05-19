package com.example.newsapp.domain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.utils.Constants.ARTICLE_TABLE

@Dao
interface ArticleDao {
    @Query("SELECT * FROM $ARTICLE_TABLE")
    suspend fun getAll(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg articles: ArticleEntity)

    @Query("DELETE FROM $ARTICLE_TABLE")
    suspend fun deleteAll()
}