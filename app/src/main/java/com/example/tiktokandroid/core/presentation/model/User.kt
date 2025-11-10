package com.example.tiktokandroid.core.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id : String = "",
    val username: String= "",
    val fullName: String= "",
    val bio: String= "",
    val dob: String= "",
    val email: String= "",
    val phone: String= "",
    val following: Int= 0,
    val followers: Int= 0,
    val likes: Int= 0,
    val profileImageUrl: String= "",
)
