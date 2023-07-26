package com.quizApp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.quizApp.data.level.AnswerModel

@Entity
data class QuestionsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val title: String,
    val answers: List<AnswerModel>
)


const val TYPE_FOOTBALL = "TYPE_FOOTBALL"
const val TYPE_BASKETBALL = "TYPE_BASKETBALL"
const val TYPE_HOCKEY = "TYPE_HOCKEY"
const val TYPE_USA_FOOTBALL = "TYPE_USA_FOOTBALL"
