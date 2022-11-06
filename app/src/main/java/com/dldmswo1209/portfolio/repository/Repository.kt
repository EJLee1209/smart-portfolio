package com.dldmswo1209.portfolio.repository

import android.content.Context
import com.dldmswo1209.portfolio.database.MyPortfolioDB
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.entity.ChatEntity
import com.dldmswo1209.portfolio.entity.TimeLineEntity
import com.dldmswo1209.portfolio.entity.UserEntity

// 모든 데이터 베이스 접근 메소드는 여기서 관리합니다.
class Repository(context: Context) {
    val db = MyPortfolioDB.getDatabase(context)


    // 카드 관련 메소드
    // 모든 카드 리스트를 가져오는 메소드
    fun getAllCard() = db.cardDao().getAllCard()

    fun insertCard(card: CardEntity) = db.cardDao().insertCard(card)

    fun updateCard(card: CardEntity) = db.cardDao().updateCard(card)

    fun deleteCard(card: CardEntity) = db.cardDao().deleteCard(card)

    // 채팅 관련 메소드
    fun getAllChat() = db.chatDao().getAllChat()

    fun insertChat(chat: ChatEntity) = db.chatDao().insertChat(chat)

    fun updateChat(chat: ChatEntity) = db.chatDao().updateChat(chat)

    fun deleteChat(chat: ChatEntity) = db.chatDao().deleteChat(chat)

    // 타임라인 관련 메소드
    fun getAllTimeLine() = db.timeLineDao().getAllTimeLine()
    fun insertTimeLine(timeLine: TimeLineEntity) = db.timeLineDao().insertTimeLine(timeLine)
    fun updateTimeLine(timeLine: TimeLineEntity) = db.timeLineDao().updateTimeLine(timeLine)
    fun deleteTimeLine(timeLine: TimeLineEntity) = db.timeLineDao().deleteTimeLine(timeLine)

    // 유저 정보 관련 메소드
    fun insertUser(user: UserEntity) = db.userDao().insertUser(user)

    fun updateUser(user: UserEntity) = db.userDao().updateUser(user)

    fun getAllUser() = db.userDao().getAllUser()

}