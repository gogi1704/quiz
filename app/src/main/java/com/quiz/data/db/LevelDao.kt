package com.quizApp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quizApp.data.db.entities.LevelEntity
import com.quizApp.data.db.entities.TYPE_BASKETBALL
import com.quizApp.data.db.entities.TYPE_FOOTBALL
import com.quizApp.data.db.entities.TYPE_HOCKEY
import com.quizApp.data.db.entities.TYPE_USA_FOOTBALL

@Dao
interface LevelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevels(levels: List<LevelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLevel(level: LevelEntity)

    @Query("SELECT*FROM levelEntity WHERE id =:id ")
    suspend fun getLevelById(id: Int): LevelEntity

    @Query("SELECT*FROM LevelEntity WHERE type ='$TYPE_FOOTBALL'")
    suspend fun getFootballLevels(): List<LevelEntity>

    @Query("SELECT*FROM LevelEntity WHERE type ='$TYPE_BASKETBALL'")
    suspend fun getBasketBallLevels(): List<LevelEntity>

    @Query("SELECT*FROM LevelEntity WHERE type ='$TYPE_HOCKEY'")
    suspend fun getHockeyLevels(): List<LevelEntity>

    @Query("SELECT*FROM LevelEntity WHERE type ='$TYPE_USA_FOOTBALL'")
    suspend fun getUsaFootballLevels(): List<LevelEntity>

    @Query("UPDATE levelEntity SET countCompleted = countCompleted+1 WHERE id=:levelId")
    suspend fun answerTrue(levelId: Int)


}