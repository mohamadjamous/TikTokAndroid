package com.example.tiktokandroid.feed.data.model

import com.example.tiktokandroid.core.presentation.model.User

sealed class FeedUiState {
    object Idle : FeedUiState()
    object Loading : FeedUiState()
    data class Success(val data: Any) : FeedUiState()
    data class Error(val message: String) : FeedUiState()
}
