package com.example.newsapp.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.domain.data.db.ArticleEntity

class ArticleAdapter(private var articles: List<ArticleEntity>, private val onItemClick: (ArticleEntity) -> Unit) :
    RecyclerView.Adapter<ArticleAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article, onItemClick)
    }

    override fun getItemCount() = articles.size

    fun updateArticles(newArticles: List<ArticleEntity>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(article: ArticleEntity, onItemClick: (ArticleEntity) -> Unit) {
            titleTextView.text = article.title
            descriptionTextView.text = article.description ?: ""
            itemView.setOnClickListener { onItemClick(article) }
        }
    }
}