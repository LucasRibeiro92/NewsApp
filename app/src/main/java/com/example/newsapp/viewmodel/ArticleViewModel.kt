package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.data.db.ArticleEntity
import com.example.newsapp.model.repository.ArticleRepository
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _articles = MutableLiveData<List<ArticleEntity>>()
    val articles: LiveData<List<ArticleEntity>> get() = _articles

    init {
        fetchArticles()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            articleRepository.fetchNewsArticles(
                onSuccess = { _articles.postValue(it) },
                onError = { it.printStackTrace() }
            )
        }
    }

    fun toggleFavorite(article: ArticleEntity) {
        viewModelScope.launch {
            articleRepository.toggleFavorite(article)
            fetchArticles()
        }
    }

    fun isArticleFavorite(url: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val isFavorite = articleRepository.isArticleFavorite(url)
            resultLiveData.postValue(isFavorite)
        }
        return resultLiveData
    }

}