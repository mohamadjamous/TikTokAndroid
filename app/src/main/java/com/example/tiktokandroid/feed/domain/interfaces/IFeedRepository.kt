package com.example.tiktokandroid.feed.domain.interfaces

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.data.model.CommentList
import dagger.Provides
import kotlinx.coroutines.flow.Flow


interface IFeedRepository {
    suspend fun fetchRemotePosts(num: Int, lastVisibleId: String?): Result<List<Post>>
    suspend fun updateLikeState(videoId : String, liked: Boolean): Result<Unit>
    suspend fun updateSavedState(videoId : String, saved: Boolean): Result<Unit>

    suspend fun getCommentList(videoId: String) : Result<CommentList>
    suspend fun createComment(comment: CommentList.Comment): Result<CommentList.Comment>
    suspend fun isVideoLiked(videoId: String, userId: String): Boolean
    suspend fun isVideoSaved(videoId: String, userId: String): Boolean
    suspend fun fetchMorePosts(num: Int, lastVisibleId: String?): Result<List<Post>>

    suspend fun cachePosts(posts: List<Post>)

    suspend fun pruneOldPosts(maxSize: Int = 500)

    fun observePosts(): Flow<List<Post>>
}
