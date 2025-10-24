package com.example.tiktokandroid.core.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id : String,
    val username: String,
    val dob: String,
    val email: String
)
