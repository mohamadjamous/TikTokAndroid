package com.example.tiktokandroid.feed.domain.interfaces

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.data.model.CommentList
import dagger.Provides


interface IFeedRepository {
    suspend fun fetchPosts(num: Int, lastVisibleId: String?): Result<List<Post>>
    suspend fun updateLikeState(videoId : String, liked: Boolean): Result<Unit>
    suspend fun updateSavedState(videoId : String, saved: Boolean): Result<Unit>

    suspend fun getCommentList(videoId: String) : Result<CommentList>
    suspend fun createComment(comment: CommentList.Comment): Result<CommentList.Comment>
    suspend fun isVideoLiked(videoId: String, userId: String): Boolean
    suspend fun isVideoSaved(videoId: String, userId: String): Boolean
}
