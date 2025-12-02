package com.example.tiktokandroid.feed.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tiktokandroid.feed.data.datasource.local.dao.PostDao
import com.example.tiktokandroid.feed.data.datasource.local.entities.PostEntity
import com.example.tiktokandroid.feed.domain.utils.Converter

@Database(entities = [PostEntity::class], exportSchema = true, version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase(){

    abstract fun postDao() : PostDao

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase? = null


        fun getDatabase(
            context: Context
        ): AppDatabase{
            // if the instance is not null then return it
            // if it is then create the database

            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "items_table"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}