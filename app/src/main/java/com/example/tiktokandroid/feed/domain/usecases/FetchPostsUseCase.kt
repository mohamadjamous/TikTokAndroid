package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val repository: IFeedRepository
){

    suspend fun fetchPosts(
        num: Int = 3,
        lastVisibleId: String? = null
    ) : Result<List<Post>>{
        return repository.fetchPosts(num, lastVisibleId)
    }

}