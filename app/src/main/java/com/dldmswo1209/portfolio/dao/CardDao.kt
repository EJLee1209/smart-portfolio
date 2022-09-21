package com.dldmswo1209.portfolio.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dldmswo1209.portfolio.entity.CardEntity

@Dao
interface CardDao{
    @Query("SELECT * FROM card_portfolio")
    fun getAllCard() : List<CardEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCard(card: CardEntity)

}

