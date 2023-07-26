package com.quizApp.data.level

data class QuestionModel(
    val id: Int = 0,
    val type: String,
    val title: String,
    val answers: List<AnswerModel>
)