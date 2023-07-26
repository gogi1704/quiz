package com.quizApp.myApp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    override fun doWork(): Result {
        val sharedPreferences =
            applicationContext.getSharedPreferences("ENERGY", Context.MODE_PRIVATE)
        var currentValue = sharedPreferences.getInt("ENERGY", 3)

        if (currentValue < 3) {
            currentValue++
            with(sharedPreferences.edit()) {
                putInt("ENERGY", currentValue)
                apply()
            }
        }

        // Укажите, была ли работа успешной
        return Result.success()
    }

}