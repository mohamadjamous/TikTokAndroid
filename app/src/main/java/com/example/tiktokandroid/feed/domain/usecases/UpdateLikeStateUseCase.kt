package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository
import javax.inject.Inject

class UpdateLikeStateUseCase @Inject constructor(
    private val repository: IFeedRepository
){

    suspend fun updateLikeState(
        videoId: String,
        liked: Boolean
    ) : Result<Unit>{
        return repository.updateLikeState(videoId, liked)
    }

}