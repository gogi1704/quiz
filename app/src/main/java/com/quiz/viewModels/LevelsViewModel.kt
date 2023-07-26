package com.quizApp.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.quizApp.data.level.LevelModel
import com.quizApp.data.level.QuestionModel
import com.quizApp.data.level.SportChecker
import com.quizApp.repository.LevelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelsViewModel @Inject constructor(
    application: Application,
    private val levelRepository: LevelRepository
) :
    AndroidViewModel(application) {
    val prefs = application.getSharedPreferences("SHARED", Context.MODE_PRIVATE)
    val energyPref = application.getSharedPreferences("ENERGY", Context.MODE_PRIVATE)
    private var isFirst = prefs.getBoolean("IS_FIRST", true)




    init {
        if (isFirst) {
            isFirst = false
            prefs.edit()
                .putBoolean("IS_FIRST", false)
                .apply()
            viewModelScope.launch {
                levelRepository.firstStart()
                levelRepository.getFootBallLevels()
            }
        } else getFootballLevels()
    }
    private var energyCounter = energyPref.getInt("ENERGY" , 3)
        set(value) {
            field = value
            energyLiveData.value = value

        }
    val energyLiveData = MutableLiveData(energyCounter)


    private var levelsData = LevelModel(0, SportChecker.Football(), listOf())
        set(value) {
            field = value
            levelsLiveData.value = value
            checker = value.typeSport

        }

    private var checker: SportChecker = SportChecker.Football()
        set(value) {
            field = value
            sportCheckerLiveData.value = value
        }


    val sportCheckerLiveData = MutableLiveData(checker)

    val levelsLiveData = MutableLiveData(levelsData)

    var questions = listOf<QuestionModel>()
        set(value) {
            field = value
            questionsLiveData.value = value
        }

    val questionsLiveData = MutableLiveData(questions)

    fun useEnergy(){
        energyCounter -= 1
    }


    fun getFootballLevels() {
        viewModelScope.launch {
            levelsData = levelRepository.getFootBallLevels()
        }

    }

    fun getBasketballLevels() {
        viewModelScope.launch {
            levelsData = levelRepository.getBasketBallLevels()
        }

    }

    fun getHockeyLevels() {
        viewModelScope.launch {
            levelsData = levelRepository.getHockeyLevels()
        }

    }

    fun getUsaFootballLevels() {
        viewModelScope.launch {
            levelsData = levelRepository.getUsaFootballLevels()
        }

    }

    fun getQuestionsByLevelId(id: Int) {
        viewModelScope.launch {
            questions = levelRepository.getQuestionsByLevelId(id)
        }

    }

    fun answerTrue(levelId: Int){
        viewModelScope.launch {
            levelRepository.answerTrue(levelId)
        }
    }
}