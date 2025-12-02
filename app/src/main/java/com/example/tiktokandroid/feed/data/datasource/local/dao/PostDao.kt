package com.example.tiktokandroid.feed.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tiktokandroid.feed.data.datasource.local.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("DELETE FROM post_table WHERE post_id IN (:ids)")
    suspend fun deleteById(ids: List<String>)

    @Query("SELECT * FROM post_table")
    suspend fun getAll(): List<PostEntity>

    @Query("SELECT * FROM post_table ORDER BY post_id ASC")
    suspend fun getAllOnce(): List<PostEntity>

    @Query("DELETE FROM post_table")
    suspend fun clearAll()

}