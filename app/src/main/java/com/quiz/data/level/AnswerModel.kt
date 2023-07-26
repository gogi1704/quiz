package com.quizApp.data.level

import androidx.room.TypeConverter

data class AnswerModel(
    val answer: String,
    val isTrue: Boolean = false
)

class AnswerConverter {
    @TypeConverter
    fun fromMyDataList(value: List<AnswerModel>): String {
        return value.joinToString(separator = ";") { "${it.answer},${it.isTrue}" }
    }

    @TypeConverter
    fun toMyDataList(value: String): List<AnswerModel> {
        return value.split(";").map {
            val parts = it.split(",")
            AnswerModel(parts[0], parts[1].toBoolean())
        }
    }
}
