package com.example.tiktokandroid.auth.data.model

import com.example.tiktokandroid.core.presentation.model.User

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val data: Any) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
