package com.example.tiktokandroid.feed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiktokandroid.auth.data.model.AuthUiState
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.data.model.FeedUiState
import com.example.tiktokandroid.feed.domain.usecases.FetchPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val fetchPostsUseCase: FetchPostsUseCase
) : ViewModel() {

    private val _videos = MutableStateFlow<List<Post>>(emptyList())
    val videos get() = _videos

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Idle)
    val uiState get() = _uiState


    private val videoURLs = listOf(
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4"
    )

    var posts: List<Post> = emptyList()
        private set

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading
            val result = fetchPostsUseCase.fetchPosts()
            _videos.value = result.getOrNull() ?: emptyList()
            _uiState.value = result.fold(
                onSuccess = { FeedUiState.Success(it) },
                onFailure = { FeedUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

}