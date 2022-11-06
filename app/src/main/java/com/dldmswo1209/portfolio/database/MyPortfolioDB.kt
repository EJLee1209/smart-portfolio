package com.dldmswo1209.portfolio.database

import android.content.Context
import androidx.room.*
import com.dldmswo1209.portfolio.dao.CardDao
import com.dldmswo1209.portfolio.dao.ChatDao
import com.dldmswo1209.portfolio.dao.TimeLineDao
import com.dldmswo1209.portfolio.dao.UserDao
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.entity.ChatEntity
import com.dldmswo1209.portfolio.entity.TimeLineEntity
import com.dldmswo1209.portfolio.entity.UserEntity

// SQLite 데이터 베이스
// Database 어노테이션 파라미터로 entities 와 version 을 지정합니다.
// version 은 데이터 베이스 구조가 바뀔 때마다 변경해줘야 합니다.
@Database(entities = [CardEntity::class, ChatEntity::class, UserEntity::class, TimeLineEntity::class], version = 11)
abstract class MyPortfolioDB : RoomDatabase() {
    abstract fun cardDao() : CardDao
    abstract fun chatDao() : ChatDao
    abstract fun userDao() : UserDao
    abstract fun timeLineDao() : TimeLineDao

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