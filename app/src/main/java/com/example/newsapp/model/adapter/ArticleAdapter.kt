package com.example.newsapp.model.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemArticleBinding
import com.example.newsapp.model.data.db.ArticleEntity
import com.example.newsapp.viewmodel.ArticleViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ArticleAdapter(
    private var articles: List<ArticleEntity>,
    private val context: Context,
    private val articleViewModel: ArticleViewModel,
    private val owner: LifecycleOwner,
    private val onArticleClick: (ArticleEntity) -> Unit,
    private val onFavoriteClick: (ArticleEntity) -> Unit,
    private val onShareClick: (ArticleEntity) -> Unit
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
            // Convert and format the date
            val formattedDate = article.publishedAt?.let { formatDateString(it) }
            binding.tvArticlePublishedAt.text = formattedDate

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

            binding.ivShareIcon.setOnClickListener {
                onShareClick(article)
            }
        }

        private fun formatDateString(dateString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date: Date? = inputFormat.parse(dateString)
            return if (date != null) {
                outputFormat.format(date)
            } else {
                dateString // Fallback to the original string in case of an error
            }
        }
    }
}