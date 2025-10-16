package com.example.tiktokandroid.notifications.data.model

data class Notification(
    val id: String,
    val username: String,
    val userPhotoUrl: String,
    val description: String,
    val time: Long,
    val photoUrl: String
)