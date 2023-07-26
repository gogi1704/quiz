package com.quizApp.repository

import com.quizApp.apiService.api.ApiService
import com.quizApp.apiService.api.NEWS_API_KEY
import com.quizApp.apiService.api.NEWS_BASE_URL
import com.quizApp.data.news.Article
import com.quizApp.data.news.NewsData
import javax.inject.Inject

interface NewsRepository {
    suspend fun getNews(): NewsData
}

class NewsRepositoryImpl @Inject constructor(private val api: ApiService) : NewsRepository {

    override suspend fun getNews(): NewsData {

        val response =
            api.newsResponse("$NEWS_BASE_URL?country=us&category=sports&apiKey=$NEWS_API_KEY")
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("")

        } else throw Exception()
    }


}