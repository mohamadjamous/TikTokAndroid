package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository


class FakeFeedRepository : IFeedRepository {
    private var postsToReturn: Result<List<Post>> = Result.success(emptyList())

    fun setFakePosts(posts: List<Post>) {
        postsToReturn = Result.success(posts)
    }

    fun setFailure(exception: Exception) {
        postsToReturn = Result.failure(exception)
    }

    override suspend fun fetchRemotePosts(num: Int, lastVisibleId: String?): Result<List<Post>> {
        return postsToReturn
    }
}



