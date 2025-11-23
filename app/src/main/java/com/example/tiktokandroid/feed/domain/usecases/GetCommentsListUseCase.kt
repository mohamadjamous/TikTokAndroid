package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.feed.data.model.CommentList
import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository
import javax.inject.Inject

class GetCommentsListUseCase @Inject constructor(
    private val repository: IFeedRepository
) {

    suspend fun getCommentsList(videoId: String): Result<CommentList>{
        return repository.getCommentList(videoId)
    }
}