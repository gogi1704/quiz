package com.quizApp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LevelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val name: String,
    val countCompleted: Int,
    val status: String = STATUS_LOCK
)

const val STATUS_LOCK = "STATUS_LOCK"
const val STATUS_STARTED = "STATUS_PROGRESS"
const val STATUS_COMPLETED = "STATUS_COMPLETED"
