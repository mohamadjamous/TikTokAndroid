package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository
import javax.inject.Inject

class VideoStatsUseCase @Inject constructor(
    private val repository: IFeedRepository
) {

    suspend fun getLikedState(
        videoId: String, userId : String
    ): Boolean{
        return repository.isVideoLiked(
            videoId, userId
        )
    }

    suspend fun getSavedState(
        videoId: String, userId : String
    ): Boolean{
        return repository.isVideoSaved(
            videoId, userId
        )
    }


}