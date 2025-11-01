package com.example.tiktokandroid.feed.domain.usecases

import androidx.compose.runtime.snapshots.SnapshotId
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.domain.repositories.FeedRepository
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val repository: FeedRepository
){

    suspend fun fetchPosts(
        num: Int = 3,
        lastVisibleId: String? = null
    ) : Result<List<Post>>{
        return repository.fetchPosts(num = num, lastVisibleId = lastVisibleId)
    }

}