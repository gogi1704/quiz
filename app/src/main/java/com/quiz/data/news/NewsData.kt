package com.quizApp.data.news

data class NewsData(
    val status:String,
    val totalResults:Int,
    val articles:List<Article>
)