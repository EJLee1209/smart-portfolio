package com.dldmswo1209.portfolio.repository

import android.content.Context
import com.dldmswo1209.portfolio.database.MyPortfolioDB
import com.dldmswo1209.portfolio.entity.CardEntity

class Repository(context: Context) {
    val db = MyPortfolioDB.getDatabase(context)

    // 모든 카드 리스트를 가져오는 메소드
    fun getAllCard() = db.cardDao().getAllCard()

    // 카드 하나를 추가하는 메소드
    fun insertCard(card: CardEntity) = db.cardDao().insertCard(card)

}