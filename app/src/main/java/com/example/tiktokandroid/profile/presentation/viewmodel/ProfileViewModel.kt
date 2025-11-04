package com.example.tiktokandroid.profile.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import com.example.tiktokandroid.feed.data.model.FeedUiState
import com.example.tiktokandroid.profile.domain.usecases.FetchUserPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userSharedPreferences: UserPreferences,
    private val fetchUserPostsUseCase: FetchUserPostsUseCase
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> get() = _posts

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser


    private val _uiState = mutableStateOf<FeedUiState>(FeedUiState.Idle)
    val uiState = _uiState


    private val _publicVideos = MutableStateFlow<List<Post>>(emptyList())
    val publicVideos: StateFlow<List<Post>> get() = _publicVideos



    init {
        fetchStoredUser()
        fetchUserPosts()
    }

    private fun fetchUserPosts(
        uid : String = _currentUser.value?.id ?: ""
    ) {

        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading
            val result = fetchUserPostsUseCase(uid)
            val posts = result.getOrNull() ?: emptyList()

            _posts.value = posts
            _uiState.value = result.fold(
                onSuccess = { FeedUiState.Success(it) },
                onFailure = { FeedUiState.Error(it.message ?: "Unknown error") }
            )
        }

    }



    fun fetchStoredUser() {
        _currentUser.value = userSharedPreferences.getUser()
        println("Debug: ${_currentUser.value?.id}")
        println("Debug: ${_currentUser.value?.username}")
    }



}