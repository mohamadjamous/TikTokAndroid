package com.example.tiktokandroid.profile.presentation.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tiktokandroid.auth.domain.repositories.UserRepository
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userSharedPreferences: UserPreferences
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> get() = _posts

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser


    init {
        fetchStoredUser()
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



    fun fetchStoredUser() {
        _currentUser.value = userSharedPreferences.getUser()
        println("Debug: ${_currentUser.value?.id}")
        println("Debug: ${_currentUser.value?.username}")
    }



}