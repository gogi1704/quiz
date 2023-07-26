package com.quizApp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.quizApp.data.news.Article
import com.quizApp.data.news.LoadState
import com.quizApp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    private val repository: NewsRepository
) : AndroidViewModel(application) {


    var news = LoadState(articles = listOf())
        set(value) {
            field = value
            newsLiveData.value = value
        }

    val newsLiveData = MutableLiveData(news)


    fun getNews() {
       news = news.copy(isLoading = true)
        viewModelScope.launch {
            try {
                news =  news. copy(articles = repository.getNews().articles)
                news = news.copy(isLoading = false)
            }catch (e:Exception){
                e
            }

        }
    }

    init {
        getNews()
    }

}


