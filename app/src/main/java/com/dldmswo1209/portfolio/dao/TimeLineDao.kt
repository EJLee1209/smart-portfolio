package com.dldmswo1209.portfolio.dao

import androidx.room.*
import com.dldmswo1209.portfolio.entity.ChatEntity
import com.dldmswo1209.portfolio.entity.TimeLineEntity

@Dao
interface TimeLineDao {
    @Query("select * from timeLine_portfolio")
    fun getAllTimeLine(): List<TimeLineEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTimeLine(timeLine: TimeLineEntity)

    @Update
    fun updateTimeLine(timeLine: TimeLineEntity)

    @Delete
    fun deleteTimeLine(timeLine: TimeLineEntity)
}