package com.example.tiktokandroid.core.presentation.model

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: String = "",
    val userId: String = "",
    val videoUrl: String = "", // <-- Firestore-safe string URL
    val likes: Long = 0,
    val comments: Long = 0,
    val allowComments: Boolean = true,
    val username: String = "", // <-- should be String, not Long
    val description: String = "",
    var datePosted: Long = 0
)
