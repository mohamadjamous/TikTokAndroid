package com.example.tiktokandroid.feed.data.datasource.local.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.example.tiktokandroid.feed.data.datasource.local.entities.PostEntity
import kotlinx.coroutines.flow.Flow


data class IdOnly(
    @ColumnInfo(name = "id") val id: String
)

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

    @Query("SELECT COUNT(*) FROM post_table")
    suspend fun countAll(): Int

    @Query("""
    SELECT id FROM post_table
    ORDER BY created_at ASC
    LIMIT :limit
    """)
    suspend fun getOldestIds(limit: Int): List<IdOnly>



    @Query("DELETE FROM post_table WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<String>)

    @Query("""
    SELECT * FROM post_table
    WHERE id > :lastId
    ORDER BY id ASC
    LIMIT :num
    """)
    suspend fun getNextPage(lastId: String?, num: Int): List<PostEntity>



}