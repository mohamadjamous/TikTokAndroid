package com.example.tiktokandroid.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiktokandroid.core.presentation.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    private val _videos = MutableStateFlow<List<Post>>(emptyList())
    val videos: StateFlow<List<Post>> get() = _videos


    init {
        fetchUserVideos()
    }

    private fun fetchUserVideos() {

        _videos.value = listOf(
            Post(id = "123", videoUrl = "123"),
            Post(id = "124", videoUrl = "123"),
            Post(id = "125", videoUrl = "123"),
            Post(id = "126", videoUrl = "123"),
            Post(id = "127", videoUrl = "123"),
            Post(id = "123", videoUrl = "123"),
            Post(id = "124", videoUrl = "123"),
            Post(id = "125", videoUrl = "123"),
            Post(id = "126", videoUrl = "123"),
            Post(id = "127", videoUrl = "123"),
            Post(id = "123", videoUrl = "123"),
            Post(id = "124", videoUrl = "123"),
            Post(id = "125", videoUrl = "123"),
            Post(id = "126", videoUrl = "123"),
            Post(id = "127", videoUrl = "123"),
        )
    }

}