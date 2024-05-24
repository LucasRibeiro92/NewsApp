package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.data.db.ArticleEntity
import com.example.newsapp.model.repository.ArticleRepository
import com.example.newsapp.utils.AppError
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _articles = MutableLiveData<List<ArticleEntity>>()
    val articles: LiveData<List<ArticleEntity>> get() = _articles

    private val _error = MutableLiveData<AppError>()
    val error: LiveData<AppError> get() = _error

    private var currentPage = 0
    private var allArticles: List<ArticleEntity> = emptyList()
    private val pageSize = 10
    var isLoading = false
        private set

    init {
        fetchArticles()
    }

    fun fetchArticles() {
        isLoading = true
        viewModelScope.launch {
            articleRepository.fetchNewsArticles(
                onSuccess = { articles ->
                    allArticles = articles
                    //_articles.postValue(articles)
                    loadInitialArticles()
                    isLoading = false
                },
                onError = { appError ->
                    _error.postValue(appError)
                    isLoading = false
                }
            )
        }
    }

    fun loadInitialArticles() {
        currentPage = 0 // Reinicia a contagem de páginas
        val initialArticles = allArticles.subList(0, minOf(9, allArticles.size))
        _articles.postValue(initialArticles)
        currentPage++
    }

    fun loadMoreArticles() {
        val start = currentPage * pageSize
        val end = (currentPage + 1) * pageSize
        if (start < allArticles.size) {
            val newArticles = allArticles.subList(start, minOf(end, allArticles.size))
            val currentList = _articles.value ?: listOf()
            _articles.postValue(currentList + newArticles)
            currentPage++
        }
    }

    fun toggleFavorite(article: ArticleEntity) {
        viewModelScope.launch {
            try {
                articleRepository.toggleFavorite(article)
                fetchArticles() // Atualiza a lista de artigos
            } catch (e: AppError.DatabaseError) {
                _error.postValue(e)
            }
        }
    }

    fun isArticleFavorite(url: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            try {
                val isFavorite = articleRepository.isArticleFavorite(url)
                resultLiveData.postValue(isFavorite)
            } catch (e: AppError.DatabaseError) {
                _error.postValue(e)
            }
        }
        return resultLiveData
    }

}