package com.example.tiktokandroid.feed.domain.interfaces

import com.example.tiktokandroid.core.presentation.model.Post
import dagger.Provides


interface IFeedRepository {
    suspend fun fetchPosts(num: Int, lastVisibleId: String?): Result<List<Post>>
    suspend fun updateLikeState(videoId : String, liked: Boolean): Result<Unit>
}
