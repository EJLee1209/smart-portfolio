package com.dldmswo1209.portfolio.dao

import androidx.room.*
import com.dldmswo1209.portfolio.entity.ChatEntity

@Dao
interface ChatDao{
    @Query("SELECT * FROM chat_portfolio")
    fun getAllChat() : List<ChatEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChat(chat: ChatEntity)

    @Update
    fun updateChat(chat: ChatEntity)

    @Delete
    fun deleteChat(chat: ChatEntity)

}