package com.example.tiktokandroid.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiktokandroid.core.presentation.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> get() = _posts


    init {
        fetchUserPosts()
    }

    private fun fetchUserPosts() {

        _posts.value = listOf(
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