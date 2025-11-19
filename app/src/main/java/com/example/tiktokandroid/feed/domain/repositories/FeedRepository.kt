package com.example.tiktokandroid.feed.domain.repositories

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.data.datasource.FeedRemoteDataSource
import com.example.tiktokandroid.feed.data.model.CommentList
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

    override suspend fun updateSavedState(
        videoId: String,
        saved: Boolean
    ): Result<Unit> {
        return dataSource.updateSavedState(
            videoId, saved
        )
    }

    override suspend fun getCommentList(videoId: String): Result<CommentList> {
        return dataSource.getCommentList(videoId)
    }

    override suspend fun createComment(comment: CommentList.Comment): Result<CommentList.Comment> {
        return dataSource.createComment(comment)
    }

    override suspend fun isVideoLiked(
        videoId: String,
        userId: String
    ): Boolean {
        return dataSource.isVideoLiked(
            videoId, userId
        )
    }

    override suspend fun isVideoSaved(
        videoId: String,
        userId: String
    ): Boolean {
        return dataSource.isVideoSaved(
            videoId, userId
        )
    }

}