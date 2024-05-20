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

    private val _favorites = MutableLiveData<List<ArticleEntity>>()
    val favorites: LiveData<List<ArticleEntity>> = _favorites

    init {
        fetchArticles()
        //loadFavorites()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            articleRepository.fetchNewsArticles(
                onSuccess = { _articles.postValue(it) },
                onError = { it.printStackTrace() }
            )
        }
    }

    /*
    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.postValue(articleRepository.getAllFavorites())
        }
    }
    */
    fun addFavorite(article: ArticleEntity) {
        viewModelScope.launch {
            articleRepository.addFavorite(article)
            //loadFavorites()
        }
    }

    fun removeFavorite(url: String) {
        viewModelScope.launch {
            articleRepository.removeFavorite(url)
            //loadFavorites()
        }
    }
}