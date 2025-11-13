package com.example.tiktokandroid.feed.data.model

import com.example.tiktokandroid.core.presentation.model.User
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Created by Puskal Khadka on 3/21/2023.
 */

@Serializable
data class CommentList(
    val videoId: String?,
    val totalComment: Int,
    val comments: List<Comment>,
    val isCommentPrivate: Boolean
) {
    @Serializable
    data class Comment(
        val commentBy: User,
        val comment: String?,
        val createdAt: String,
        val totalLike: Long,
        val totalDisLike: Long,
        val threadCount: Int,
        val thread: List<Comment>
    )
}

data class CommentText(
    val comment: String
)