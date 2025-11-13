package com.example.tiktokandroid.feed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktokandroid.feed.data.model.CommentList
import com.example.tiktokandroid.feed.data.model.FeedUiState
import com.example.tiktokandroid.feed.domain.usecases.GetCommentsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class CommentListViewModel @Inject constructor(
    private val getCommentsListUseCase: GetCommentsListUseCase
): ViewModel() {


    private val _commentsList = MutableStateFlow<CommentList?>(null)
    val commentsList: StateFlow<CommentList?> get() = _commentsList

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Idle)
    val uiState: StateFlow<FeedUiState> get() = _uiState

    
    fun getCommentsList(videoId: String){
        viewModelScope.launch {

            _uiState.value = FeedUiState.Loading
            val result = getCommentsListUseCase.getCommentsList(videoId)
            val list = result.getOrNull()
            if (list != null) {
                _commentsList.value = list
            }

            _uiState.value = result.fold(
                onSuccess = { FeedUiState.Success(it)},
                onFailure = { FeedUiState.Error(it.message ?: "Unknown Error") }
            )
        }
    }


}