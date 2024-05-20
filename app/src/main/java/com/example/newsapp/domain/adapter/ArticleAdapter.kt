package com.example.newsapp.domain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemArticleBinding
import com.example.newsapp.domain.data.db.ArticleEntity
import com.example.newsapp.domain.viewmodel.ArticleViewModel

class ArticleAdapter(
    private var articles: List<ArticleEntity>,
    private val context: Context,
    private val articleViewModel: ArticleViewModel,
    private val owner: LifecycleOwner,
    private val onArticleClick: (ArticleEntity) -> Unit,
    private val onFavoriteClick: (ArticleEntity) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = articles.size

    fun updateArticles(newArticles: List<ArticleEntity>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleEntity) {
            binding.tvArticleTitle.text = article.title

            Glide.with(binding.ivArticleImage.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_android_black_24dp)
                .into(binding.ivArticleImage)

            articleViewModel.isArticleFavorite(article.url).observe(owner) { isFavorite ->
                val favoriteIconRes = if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                binding.ivFavoriteIcon.setImageResource(favoriteIconRes)
            }

            binding.root.setOnClickListener { onArticleClick(article) }

            binding.ivFavoriteIcon.setOnClickListener {
                onFavoriteClick(article)
            }
        }
    }
}