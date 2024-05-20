package com.example.newsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import com.example.newsapp.domain.adapter.ArticleAdapter
import com.example.newsapp.domain.viewmodel.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val articleViewModel: ArticleViewModel by viewModel()
    private lateinit var rvArticleMain: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvArticleMain = findViewById(R.id.rvArticleMain)
        rvArticleMain.layoutManager = LinearLayoutManager(this)

        articleAdapter = ArticleAdapter(listOf()) { article ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(intent)
        }
        rvArticleMain.adapter = articleAdapter

        articleViewModel.articles.observe(this, Observer { articles ->
            articles?.let {
                articleAdapter.updateArticles(it)
            }
        })

        // Swipe to refresh
        findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).setOnRefreshListener {
            articleViewModel.fetchArticles()
        }
    }
}