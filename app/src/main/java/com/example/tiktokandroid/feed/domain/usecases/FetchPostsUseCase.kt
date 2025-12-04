package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val repo: IFeedRepository
) {

    suspend operator fun invoke(
        num: Int,
        lastVisibleId: String? = null
    ): Result<List<Post>> {
        return repo.fetchRemotePosts(num, lastVisibleId)
            .map { posts ->
                posts.sortedByDescending { it.createdAt }
            }
    }


    suspend fun fetchLocalPosts(
        num : Int,
        lastVisibleId: String? = null): Result<List<Post>> {
        return repo.fetchMorePosts(num, lastVisibleId)
    }

    suspend fun cachePosts(posts: List<Post>) {
        println("CachePostsSize: ${posts.size}")
        repo.cachePosts(posts)
    }
}