package com.dldmswo1209.portfolio.entity

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// card_portfolio 테이블 구조를 생성합니다.
@Entity(tableName = "card_portfolio")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "image")
    var image: String? = null,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "link")
    var link: String? = null
)
