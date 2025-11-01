package com.example.tiktokandroid.feed.domain.repositories

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.data.datasource.FeedRemoteDataSource
import javax.inject.Inject


class FeedRepository @Inject constructor(
    private val dataSource: FeedRemoteDataSource
) {

    suspend fun fetchPosts(
        num: Int,
        lastVisibleId: String?
    ) : Result<List<Post>>{
        return dataSource.fetchPosts(num  = num, lastVisibleId = lastVisibleId)
    }
}