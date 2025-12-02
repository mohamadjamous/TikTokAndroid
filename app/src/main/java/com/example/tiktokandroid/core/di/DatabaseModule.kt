package com.example.tiktokandroid.core.di

import android.content.Context
import androidx.room.Room
import com.example.tiktokandroid.feed.data.datasource.local.dao.PostDao
import com.example.tiktokandroid.feed.data.datasource.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "items_table"
        ).build()
    }

    @Provides
    fun providePostDao(db: AppDatabase): PostDao {
        return db.postDao()
    }
}