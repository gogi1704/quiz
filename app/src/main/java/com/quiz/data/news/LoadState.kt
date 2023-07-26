package com.quizApp.data.news

data class LoadState(
    var isLoading:Boolean = false,
    var articles:List<Article>
)
