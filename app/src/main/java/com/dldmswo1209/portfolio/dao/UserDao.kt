package com.dldmswo1209.portfolio.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dldmswo1209.portfolio.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)

    @Query("SELECT * FROM user_info")
    fun getAllUser() : List<UserEntity>
}