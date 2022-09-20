package com.dldmswo1209.portfolio.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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

// type
const val MY_CHAT = 1
const val OTHER_CHAT = 0