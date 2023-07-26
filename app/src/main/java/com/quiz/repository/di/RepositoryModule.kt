package com.quizApp.repository.di

import com.quizApp.repository.LevelRepository
import com.quizApp.repository.LevelRepositoryImpl
import com.quizApp.repository.NewsRepository
import com.quizApp.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsRepositoryImpl(impl: LevelRepositoryImpl): LevelRepository

    @Binds
    @Singleton
    fun bindsNewsRepositoryImpl(impl: NewsRepositoryImpl): NewsRepository
}