package com.quizApp.ui.adapters.newsAdapter

import androidx.recyclerview.widget.DiffUtil
import com.quizApp.data.news.Article

class NewsCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem.title == newItem.title


    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem
}