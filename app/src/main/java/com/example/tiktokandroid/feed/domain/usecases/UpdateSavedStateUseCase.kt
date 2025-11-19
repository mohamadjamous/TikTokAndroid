package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository
import javax.inject.Inject

class UpdateSavedStateUseCase @Inject constructor(
    private val repository: IFeedRepository
){

    suspend fun updateSavedState(
        videoId: String,
        saved: Boolean
    ) : Result<Unit>{
        return repository.updateSavedState(videoId, saved)
    }

}