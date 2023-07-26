package com.quizApp.myApp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate()
    }
}