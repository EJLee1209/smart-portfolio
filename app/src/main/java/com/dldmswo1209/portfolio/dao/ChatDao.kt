package com.dldmswo1209.portfolio.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dldmswo1209.portfolio.entity.ChatEntity

@Dao
interface ChatDao{
    @Query("SELECT * FROM chat_portfolio")
    fun getAllChat() : List<ChatEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChat(card: ChatEntity)



}