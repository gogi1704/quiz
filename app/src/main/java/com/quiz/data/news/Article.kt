package com.quizApp.data.news

data class Article(
    val source: Source,
    val author: Any,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
)