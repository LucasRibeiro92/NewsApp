package com.example.newsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.domain.adapter.ArticleAdapter
import com.example.newsapp.domain.viewmodel.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val articleViewModel: ArticleViewModel by viewModel()
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBindings()
        setupRecyclerView()
        setupSwipeRefresh()

        observeViewModel()
    }

    private fun setupBindings() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupRecyclerView() {
        binding.rvArticleMain.layoutManager = LinearLayoutManager(this)

        // Fornecer uma referência ao LifecycleOwner ao criar o ArticleAdapter
        articleAdapter = ArticleAdapter(
            emptyList(),
            articleViewModel,
            this, // Passando uma referência ao LifecycleOwner
            { article ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            }
        ) { article ->
            articleViewModel.toggleFavorite(article)
        }

        binding.rvArticleMain.adapter = articleAdapter
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            articleViewModel.fetchArticles()
        }
    }

    private fun observeViewModel() {
        articleViewModel.articles.observe(this) { articles ->
            articles?.let {
                articleAdapter.updateArticles(it)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}