package com.example.newsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.domain.adapter.ArticleAdapter
import com.example.newsapp.domain.viewmodel.ArticleViewModel
import com.example.newsapp.utils.SnackbarAssistant
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    /*
    * Declaring general variables
    */
    //viewBind
    private lateinit var binding: ActivityMainBinding
    //Article View Model by Koin DI
    private val articleViewModel: ArticleViewModel by viewModel()
    //Adapter to feed RV
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //Setups
        setupBindings()
        setupRecyclerView()
        setupSwipeRefresh()
        //Observers
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
            this,
            articleViewModel,
            this, // Passando uma referência ao LifecycleOwner
            { article ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            }
        ) { article ->
            articleViewModel.toggleFavorite(article)
            SnackbarAssistant.showSnackbar(binding.root, "Favorite updated")
        }

        binding.rvArticleMain.adapter = articleAdapter

    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            articleViewModel.fetchArticles()
            // Mostrar Snackbar quando a lista for atualizada
            SnackbarAssistant.showSnackbar(binding.root, "Articles list is up to date")
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