package com.quizApp.data.level

data class LevelData(
    val levelModelId :Int = 0,
    val levelName: String,
    val statusState: StatusState,
    val countCompleted: Int = 0

)
