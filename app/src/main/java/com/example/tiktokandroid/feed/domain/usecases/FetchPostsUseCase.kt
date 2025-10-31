package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.domain.repositories.FeedRepository
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val repository: FeedRepository
){

    suspend fun fetchPosts() : Result<List<Post>>{
        return repository.fetchPosts()
    }

}