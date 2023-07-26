package com.quizApp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quizApp.data.db.entities.QuestionsEntity
import com.quizApp.data.db.entities.TYPE_BASKETBALL
import com.quizApp.data.db.entities.TYPE_FOOTBALL
import com.quizApp.data.db.entities.TYPE_HOCKEY
import com.quizApp.data.db.entities.TYPE_USA_FOOTBALL

@Dao
interface QuestionsDao {

    @Insert
  suspend  fun insertQuestions(list: List<QuestionsEntity>)

    @Query("SELECT * FROM QuestionsEntity WHERE type ='$TYPE_FOOTBALL' ")
  suspend  fun getAllFootball():List<QuestionsEntity>

    @Query("SELECT * FROM QuestionsEntity WHERE type ='$TYPE_BASKETBALL' ")
    suspend  fun getAllBasketBall():List<QuestionsEntity>

    @Query("SELECT * FROM QuestionsEntity WHERE type ='$TYPE_HOCKEY' ")
    suspend  fun getAllHockey():List<QuestionsEntity>

    @Query("SELECT * FROM QuestionsEntity WHERE type ='$TYPE_USA_FOOTBALL' ")
    suspend  fun getAllUsaFootbal():List<QuestionsEntity>



}