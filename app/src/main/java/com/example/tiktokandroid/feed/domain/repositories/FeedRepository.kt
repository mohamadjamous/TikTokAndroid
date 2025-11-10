package com.example.tiktokandroid.feed.domain.repositories

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.data.datasource.FeedRemoteDataSource
import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository
import javax.inject.Inject



class FeedRepository @Inject constructor(
    private val dataSource: FeedRemoteDataSource
) : IFeedRepository {

    override suspend fun fetchPosts(
        num: Int,
        lastVisibleId: String?
    ): Result<List<Post>> {
        return dataSource.fetchPosts(num  = num, lastVisibleId = lastVisibleId)
    }

    override suspend fun updateLikeState(
        videoId: String,
        liked: Boolean
    ): Result<Unit> {
        return dataSource.updateLikeState(
            videoId, liked
        )
    }


}