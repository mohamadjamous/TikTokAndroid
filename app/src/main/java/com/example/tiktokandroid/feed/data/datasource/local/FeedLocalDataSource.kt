package com.example.tiktokandroid.feed.data.datasource.local

import com.example.tiktokandroid.feed.data.datasource.local.dao.PostDao
import com.example.tiktokandroid.feed.data.datasource.local.entities.PostEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedLocalDataSource @Inject constructor(private val postDao: PostDao) {

    suspend fun getAllPosts(): List<PostEntity>{
        return postDao.getAll()
    }


   suspend fun getAllPostsOnce(): List<PostEntity>{
        return postDao.getAllOnce()
    }

    suspend fun deletePosts(postIds: List<String>){
        postDao.deleteById(postIds)
    }


    suspend fun insertPosts(post: List<PostEntity>){
        postDao.insertAll(post)
    }

    suspend fun clearAndInsert(posts: List<PostEntity>) {
        postDao.clearAll()
        postDao.insertAll(posts)
    }

}