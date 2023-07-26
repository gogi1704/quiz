package com.quizApp.ui.adapters.newsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quizApp.R
import com.quizApp.data.news.Article
import com.quizApp.databinding.NewsItemLayoutBinding

class NewsAdapter : ListAdapter<Article, NewsAdapter.NewsViewHolder>(NewsCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            NewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class NewsViewHolder(private val binding: NewsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            with(binding) {
                textTitle.text = item.title
                textContent.text = item.content
                if (item.urlToImage.isNullOrEmpty()) {
                    image.setImageResource(R.drawable.img_american_fottbnall)
                } else {
                    image.getImage(item.urlToImage)
                }
                date.text = item.publishedAt.substringBefore("T")

            }
        }

    }
}

fun ImageView.getImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.img_american_fottbnall)
        .timeout(10_000)
        .into(this)
}