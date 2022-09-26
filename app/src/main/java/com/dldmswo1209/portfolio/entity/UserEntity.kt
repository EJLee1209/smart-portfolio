package com.dldmswo1209.portfolio.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// user_info 테이블 구조 생성
@Entity(tableName = "user_info")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "profileImage")
    var profileImage: String? = null,
    @ColumnInfo(name = "phone")
    var phone: String,
    @ColumnInfo(name = "intro")
    var intro: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "address")
    var address: String
)
