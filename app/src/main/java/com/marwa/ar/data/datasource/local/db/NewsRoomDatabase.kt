package com.marwa.ar.data.datasource.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marwa.ar.domain.entity.NewsEntity

@Database(entities = [NewsEntity::class], version =1, exportSchema = true)
abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsRoomDatabase? = null

        fun getInstance(context: Context): NewsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = buildMyDB(context)
                INSTANCE = instance
                INSTANCE!!
            }
        }

        private fun buildMyDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsRoomDatabase::class.java,
                "News.db"
            ).fallbackToDestructiveMigration().build()

    }


}