package com.quizApp.apiService.api

import com.quizApp.data.news.Article
import com.quizApp.data.news.NewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


const val NEWS_API_KEY = "484b05f2fc4f4f158f18be170b7796ae"
const val NEWS_BASE_URL = "https://newsapi.org/v2/top-headlines/"


interface ApiService {



    @GET
    suspend fun newsResponse(
        @Url url: String,
    ): Response<NewsData>



}