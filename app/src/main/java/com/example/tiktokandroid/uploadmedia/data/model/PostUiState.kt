package com.example.tiktokandroid.uploadmedia.data.model

sealed class PostUiState {
    object Idle : PostUiState()
    object Loading : PostUiState()
    data class Success(val data: Any) : PostUiState()
    data class Error(val message: String) : PostUiState()
}