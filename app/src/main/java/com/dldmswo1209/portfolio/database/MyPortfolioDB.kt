package com.dldmswo1209.portfolio.database

import android.content.Context
import androidx.room.*
import com.dldmswo1209.portfolio.dao.CardDao
import com.dldmswo1209.portfolio.dao.ChatDao
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.entity.ChatEntity

@Database(entities = [CardEntity::class, ChatEntity::class], version = 5)
abstract class MyPortfolioDB : RoomDatabase() {
    abstract fun cardDao() : CardDao
    abstract fun chatDao() : ChatDao

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
                    "text_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }


}