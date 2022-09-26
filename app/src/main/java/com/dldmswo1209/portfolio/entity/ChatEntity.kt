package com.dldmswo1209.portfolio.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// chat_portfolio 테이블 구조 생성
@Entity(tableName = "chat_portfolio")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "type")
    var type: Int
)

// type(일반모드와 관리자 모드를 구분하기 위한 플래그)
const val MY_CHAT = 1
const val OTHER_CHAT = 0