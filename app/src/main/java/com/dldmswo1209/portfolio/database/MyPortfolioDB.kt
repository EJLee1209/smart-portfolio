package com.dldmswo1209.portfolio.database

import android.content.Context
import androidx.room.*
import com.dldmswo1209.portfolio.dao.CardDao
import com.dldmswo1209.portfolio.dao.ChatDao
import com.dldmswo1209.portfolio.dao.UserDao
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.entity.ChatEntity
import com.dldmswo1209.portfolio.entity.UserEntity

@Database(entities = [CardEntity::class, ChatEntity::class, UserEntity::class], version = 10)
abstract class MyPortfolioDB : RoomDatabase() {
    abstract fun cardDao() : CardDao
    abstract fun chatDao() : ChatDao
    abstract fun userDao() : UserDao

    companion object{
        @Volatile
        private var INSTANCE: MyPortfolioDB? = null

        fun getDatabase(
            context: Context
        ) : MyPortfolioDB {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyPortfolioDB::class.java,
                    "portfolioDB"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }


}