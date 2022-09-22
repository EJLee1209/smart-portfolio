package com.dldmswo1209.portfolio.dao

import androidx.room.*
import com.dldmswo1209.portfolio.entity.CardEntity

@Dao
interface CardDao{
    @Query("SELECT * FROM card_portfolio")
    fun getAllCard() : List<CardEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCard(card: CardEntity)

    @Update
    fun updateCard(card: CardEntity)

    @Delete
    fun deleteCard(card: CardEntity)

}

