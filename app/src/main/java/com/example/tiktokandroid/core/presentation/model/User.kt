package com.example.tiktokandroid.core.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id : String,
    val username: String,
    val dob: String,
    val email: String,
    val phone: String,
    val following: Int,
    val followers: Int,
    val likes: Int,
    val profileImageUrl: String,
)
