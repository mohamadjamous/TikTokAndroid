package com.example.tiktokandroid.uploadmedia.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import com.example.tiktokandroid.uploadmedia.data.model.PostUiState
import com.example.tiktokandroid.uploadmedia.domain.usecases.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
    private val userSharedPreferences: UserPreferences
): ViewModel() {

    private val _uiState = MutableStateFlow<PostUiState>(PostUiState.Idle)
    val uiState: StateFlow<PostUiState> get() = _uiState

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    init {
        fetchStoredUser()
    }

    fun uploadPost(post: Post, videoUri: Uri){

        viewModelScope.launch {
            _uiState.value = PostUiState.Loading

            // Set current date
            post.datePosted = Date().time

            val result = postUseCase(post, videoUri)
            _uiState.value = result.fold(
                onSuccess = { PostUiState.Success(it) },
                onFailure = { PostUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }


    fun fetchStoredUser() {
        _currentUser.value = userSharedPreferences.getUser()
        println("Debug: ${_currentUser.value?.id}")
        println("Debug: ${_currentUser.value?.username}")
    }

    fun resetUiState() {
        _uiState.value = PostUiState.Idle
    }
}