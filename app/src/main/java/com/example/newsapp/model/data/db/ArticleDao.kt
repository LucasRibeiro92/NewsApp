package com.example.newsapp.model.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.utils.Constants.ARTICLE_TABLE

@Dao
interface ArticleDao {
    @Query("SELECT * FROM $ARTICLE_TABLE WHERE url = :url")
    fun getArticleByUrl(url: String): ArticleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: ArticleEntity)

    @Query("DELETE FROM $ARTICLE_TABLE WHERE url = :url")
    fun deleteByUrl(url: String): Int
}