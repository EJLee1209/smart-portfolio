package com.dldmswo1209.portfolio.repository

import android.content.Context
import com.dldmswo1209.portfolio.database.MyPortfolioDB
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.entity.ChatEntity
import com.dldmswo1209.portfolio.entity.UserEntity

class Repository(context: Context) {
    val db = MyPortfolioDB.getDatabase(context)

    // 모든 카드 리스트를 가져오는 메소드
    fun getAllCard() = db.cardDao().getAllCard()

    // 카드 하나를 추가하는 메소드
    fun insertCard(card: CardEntity) = db.cardDao().insertCard(card)

    fun getAllChat() = db.chatDao().getAllChat()

    fun insertChat(chat: ChatEntity) = db.chatDao().insertChat(chat)

    fun insertUser(user: UserEntity) = db.userDao().insertUser(user)

    fun updateUser(user: UserEntity) = db.userDao().updateUser(user)

    fun getAllUser() = db.userDao().getAllUser()

}