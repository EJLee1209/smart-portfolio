package com.dldmswo1209.portfolio.dao

import androidx.room.*
import com.dldmswo1209.portfolio.entity.CardEntity

// SQLite 데이터 베이스에 접근 하기 위한 Dao
// Data Access Object
// card_portfolio 테이블의 데이터를 관리함
@Dao
interface CardDao{
    // 모든 카드 리스트를 가져옴
    @Query("SELECT * FROM card_portfolio ORDER BY id")
    fun getAllCard() : List<CardEntity>

    // 카드 추가
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCard(card: CardEntity)

    // 카드 수정
    @Update
    fun updateCard(card: CardEntity)

    // 카드 삭제
    @Delete
    fun deleteCard(card: CardEntity)

}

