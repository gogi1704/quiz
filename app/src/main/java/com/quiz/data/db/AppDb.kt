package com.quizApp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.quizApp.data.db.entities.LevelEntity
import com.quizApp.data.db.entities.QuestionsEntity
import com.quizApp.data.level.AnswerConverter

@Database(entities = [QuestionsEntity::class , LevelEntity::class], version = 1)
@TypeConverters(AnswerConverter::class)
abstract class AppDb : RoomDatabase() {
    abstract fun questionsDao(): QuestionsDao
    abstract fun levelDao(): LevelDao

}