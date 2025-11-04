package com.example.tiktokandroid.core.presentation.model

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: String = "",
    val userId: String = "",
    val videoUrl: String = "",
    val likes: Long = 0,
    val comments: Long = 0,
    val allowComments: Boolean = true,
    val username: String = "",
    val description: String = "",
    var datePosted: Long = 0,
    var views: Long = 0,
    var publicProfile: Boolean = true,
)
