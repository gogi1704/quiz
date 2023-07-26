package com.quizApp.data.level

sealed class StatusState {
    object Started : StatusState()
    object Lock : StatusState()
    object Complete : StatusState()
}

data class LevelModel(
    val id:Int = 0,
    val typeSport: SportChecker,
    val list:List<LevelData>
)




