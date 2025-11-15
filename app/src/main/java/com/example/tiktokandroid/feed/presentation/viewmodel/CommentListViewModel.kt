package com.example.tiktokandroid.feed.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import com.example.tiktokandroid.feed.data.model.CommentList
import com.example.tiktokandroid.feed.data.model.FeedUiState
import com.example.tiktokandroid.feed.domain.usecases.CreateCommentUseCase
import com.example.tiktokandroid.feed.domain.usecases.GetCommentsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class CommentListViewModel @Inject constructor(
    private val getCommentsListUseCase: GetCommentsListUseCase,
    private val userSharedPreferences: UserPreferences,
    private val createCommentUseCase: CreateCommentUseCase
): ViewModel() {

    private val _commentUiState = MutableStateFlow<FeedUiState>(FeedUiState.Idle)
    val commentUiState: StateFlow<FeedUiState> = _commentUiState

    private val _commentsList = MutableStateFlow<List<CommentList.Comment>>(emptyList())
    val commentsList: StateFlow<List<CommentList.Comment>> = _commentsList

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Idle)
    val uiState: StateFlow<FeedUiState> get() = _uiState


    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    init {
        fetchStoredUser()
    }
    
    fun getCommentsList(videoId: String){
        viewModelScope.launch {

            _uiState.value = FeedUiState.Loading
            val result = getCommentsListUseCase.getCommentsList(videoId)
            val commentList = result.getOrNull()

            if (commentList != null) {
                _commentsList.value = commentList.comments
            }

            _uiState.value = result.fold(
                onSuccess = { FeedUiState.Success(it) },
                onFailure = { FeedUiState.Error(it.message ?: "Unknown Error") }
            )
        }
    }

    fun fetchStoredUser() {
        _currentUser.value = userSharedPreferences.getUser()
    }


    fun uploadComment(comment: CommentList.Comment) {
        viewModelScope.launch {
            _commentUiState.value = FeedUiState.Loading
            try {
                val result = createCommentUseCase.createComment(comment)
                if (result.isSuccess) {
                    _commentsList.value = _commentsList.value + comment
                    _commentUiState.value = FeedUiState.Success(comment)
                } else {
                    _commentUiState.value = FeedUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _commentUiState.value = FeedUiState.Error(e.message ?: "Unknown error")
            }
        }
    }



}