package com.example.tiktokandroid.core.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: String = "",
    val userId: String = "",
    val videoUrl: String = "",
    val authorDetails: User = User(),
    val videoStats: VideoStats = VideoStats(),
    val allowComments: Boolean = true,
    val username: String = "",
    val description: String = "",
    var createdAt: Long = 0,
    val currentViewerInteraction: ViewerInteraction = ViewerInteraction(),
    val audioModel: AudioModel? = null,
    val hasTag: List<HasTag> = listOf(),
) {

    @Serializable
    data class HasTag(
        val id: Long,
        val title: String
    )

    @Serializable
    data class ViewerInteraction(
        var isLikedByYou: Boolean = false,
        var isAddedToFavourite: Boolean = false
    )
}

/**
 * Created by Puskal Khadka on 3/18/2023.
 */
@Serializable
data class VideoStats(
    var likes: Long = 13,
    var comments: Long = 50,
    var shares: Long = 33,
    var favourites: Long = 2,
    var views: Long = (likes.plus(500)..likes.plus(100000)).random()
)

/**
 * Created by Puskal Khadka on 3/18/2023.
 */
@Serializable
data class AudioModel(
    val audioCoverImage: String,
    val isOriginal: Boolean,
    val audioAuthor: User,
    val numberOfPost: Long,
    val originalVideoUrl: String,
)