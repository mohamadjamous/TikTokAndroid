package com.example.tiktokandroid.auth.data.model

data class SignupUiState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val dob: String = "",
    val phoneNumber: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)