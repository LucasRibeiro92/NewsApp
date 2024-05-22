package com.example.newsapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.model.adapter.ArticleAdapter
import com.example.newsapp.model.data.db.ArticleEntity
import com.example.newsapp.utils.AppError
import com.example.newsapp.viewmodel.ArticleViewModel
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

        val layoutManager = LinearLayoutManager(this)
        binding.rvArticleMain.layoutManager = layoutManager
        // Fornecer uma referência ao LifecycleOwner ao criar o ArticleAdapter
        articleAdapter = ArticleAdapter(
            emptyList(),
            this,
            articleViewModel,
            this, // Passando uma referência ao LifecycleOwner
            {
                article ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            },{
                article ->
                articleViewModel.toggleFavorite(article)
                SnackbarAssistant.showSnackbar(binding.root, "Favorite updated")
            },{
                article ->
                shareArticle(article)
            }
        )

        binding.rvArticleMain.adapter = articleAdapter

        binding.rvArticleMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!articleViewModel.isLoading && visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    articleViewModel.loadMoreArticles()
                }
            }
        })
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            articleViewModel.fetchArticles()
            // Mostrar Snackbar quando a lista for atualizada
            SnackbarAssistant.showSnackbar(binding.root, "News refreshed")
        }
    }

    private fun observeViewModel() {

        articleViewModel.articles.observe(this) { articles ->
            articles?.let {
                articleAdapter.updateArticles(it)
                binding.swipeRefreshLayout.isRefreshing = false
           }
        }

        articleViewModel.error.observe(this) { error ->
            error?.let {
                val message = when (error) {
                    is AppError.NetworkError -> error.message
                    is AppError.DatabaseError -> error.message
                    is AppError.UnknownError -> error.message
                }
                if (message != null) {
                    SnackbarAssistant.showSnackbar(binding.root, message)
                }
            }
        }
    }

    private fun shareArticle(article: ArticleEntity) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check this: ${article.title}\n\nRead more: ${article.url}")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share article with:"))
    }
}