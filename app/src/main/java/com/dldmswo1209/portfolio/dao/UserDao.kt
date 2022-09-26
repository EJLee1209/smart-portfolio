package com.dldmswo1209.portfolio.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dldmswo1209.portfolio.entity.UserEntity

// user_info 테이블의 데이터를 관리
@Dao
interface UserDao {
    // 유저 추가
    @Insert
    fun insertUser(user: UserEntity)

    // 유저 업데이트
    @Update
    fun updateUser(user: UserEntity)

    // 모든 유저 가져오기
    @Query("SELECT * FROM user_info")
    fun getAllUser() : List<UserEntity>
}