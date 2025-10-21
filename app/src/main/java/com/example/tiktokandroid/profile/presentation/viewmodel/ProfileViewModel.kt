package com.example.tiktokandroid.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.profile.domain.repositories.ProfileRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {

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