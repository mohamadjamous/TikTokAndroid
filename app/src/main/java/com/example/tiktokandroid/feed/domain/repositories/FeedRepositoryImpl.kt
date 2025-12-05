package com.example.tiktokandroid.feed.domain.repositories

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.data.datasource.local.FeedLocalDataSource
import com.example.tiktokandroid.feed.data.datasource.remote.FeedRemoteDataSource
import com.example.tiktokandroid.feed.data.datasource.remote.NetworkHandler
import com.example.tiktokandroid.feed.data.model.CommentList
import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository
import com.example.tiktokandroid.feed.domain.utils.PostMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val remote: FeedRemoteDataSource,
    private val local: FeedLocalDataSource,
    private val networkHandler: NetworkHandler,
    private val postMapper: PostMapper
) : IFeedRepository {


    override suspend fun updateLikeState(
        videoId: String,
        liked: Boolean
    ): Result<Unit> {
        return remote.updateLikeState(
            videoId, liked
        )
    }

    override suspend fun updateSavedState(
        videoId: String,
        saved: Boolean
    ): Result<Unit> {
        return remote.updateSavedState(
            videoId, saved
        )
    }

    override suspend fun getCommentList(videoId: String): Result<CommentList> {
        return remote.getCommentList(videoId)
    }

    override suspend fun createComment(comment: CommentList.Comment): Result<CommentList.Comment> {
        return remote.createComment(comment)
    }

    override suspend fun isVideoLiked(
        videoId: String,
        userId: String
    ): Boolean {
        return remote.isVideoLiked(
            videoId, userId
        )
    }

    override suspend fun isVideoSaved(
        videoId: String,
        userId: String
    ): Boolean {
        return remote.isVideoSaved(
            videoId, userId
        )
    }


    /**
     * Fetch from remote source (Firebase)
     */
    override suspend fun fetchRemotePosts(num: Int, lastVisibleId: String?): Result<List<Post>> {

        return remote.fetchPosts(num = num, lastVisibleId = lastVisibleId)
    }

    /**
     * Fetch more posts as per paging algorithm from room
     */
    override suspend fun fetchMorePosts(
        num: Int,
        lastVisibleId: String?
    ): Result<List<Post>> {

        // Try to load next chunk from ROOM
        val cachedNext = local.getNextPage(lastVisibleId, num)

        // Convert and return cached data
        val mapped = cachedNext.map(postMapper::mapToDomain)

        return Result.success(mapped)
    }


    /**
     * Insert posts into local cache
     */
    override suspend fun cachePosts(posts: List<Post>) {
        if (posts.isEmpty()) return
        val entities = posts
            .distinctBy { it.id }
            .map(postMapper::mapToEntity)

        if (entities.isNotEmpty()) {
            local.insertPosts(entities)
        }
    }

    override suspend fun pruneOldPosts(maxSize: Int) {
        val total = local.countAll()

        if (total > maxSize) {
            val numberToDelete = total - maxSize

            val toDeleteIds = local.getOldestIds(numberToDelete)

            local.deleteByIds(toDeleteIds)
        }
    }



    private suspend fun refreshRemoteInBackground() {
        if (!networkHandler.isNetworkAvailable()) return

        val result = remote.fetchPosts(num = 10, lastVisibleId = null)
        result.onSuccess { posts ->
            local.deletePosts(posts.map { it.id })
        }
    }

    override fun observePosts(): Flow<List<Post>> =
        local.observePosts().map { list ->
            list.map(postMapper::mapToDomain)
        }


}