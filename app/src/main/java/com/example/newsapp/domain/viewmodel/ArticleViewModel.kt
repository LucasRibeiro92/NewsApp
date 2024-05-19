package com.example.newsapp.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.data.db.ArticleEntity
import com.example.newsapp.domain.repository.ArticleRepository
import kotlinx.coroutines.launch
class ArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<ArticleEntity>>()
    val articles: LiveData<List<ArticleEntity>> = _articles

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            articleRepository.fetchArticles()
            _articles.postValue(articleRepository.getSavedArticles())
        }
    }
}