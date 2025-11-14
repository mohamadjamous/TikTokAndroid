package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.feed.data.model.CommentList
import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository
import org.w3c.dom.Comment
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(
    private val repository: IFeedRepository
) {

    suspend fun createComment(comment: CommentList.Comment): Result<CommentList.Comment>{
        return repository.createComment(comment)
    }
}