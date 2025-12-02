package com.example.tiktokandroid.feed.domain.utils

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.data.datasource.local.entities.PostEntity
import jakarta.inject.Inject

class PostMapper @Inject constructor() {

    fun mapToEntity(post: Post): PostEntity {
        return PostEntity(
            postId = post.id,
            userId = post.userId,
            videoUrl = post.videoUrl,
            authorDetails = post.author,
            videoStats = post.videoStats,
            currentViewerInteraction = post.currentViewerInteraction,
            audioModel = post.audioModel,
            allowComments = post.allowComments,
            username = post.username,
            description = post.description,
            createdAt = post.createdAt,
            hasTags = post.hasTag
        )
    }

    fun mapToDomain(entity: PostEntity): Post {
        return Post(
            id = entity.postId,
            userId = entity.userId,
            videoUrl = entity.videoUrl,
            author = entity.authorDetails,
            videoStats = entity.videoStats,
            currentViewerInteraction = entity.currentViewerInteraction,
            audioModel = entity.audioModel,
            allowComments = entity.allowComments,
            username = entity.username,
            description = entity.description,
            createdAt = entity.createdAt,
            hasTag = entity.hasTags  // matches domain field
        )
    }
}
