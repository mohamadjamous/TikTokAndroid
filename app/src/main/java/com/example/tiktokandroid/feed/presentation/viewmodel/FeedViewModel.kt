package com.example.tiktokandroid.feed.presentation.viewmodel

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import com.example.tiktokandroid.auth.data.model.AuthUiState
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.data.model.FeedUiState
import com.example.tiktokandroid.feed.domain.usecases.FetchPostsUseCase
import com.example.tiktokandroid.feed.presentation.view.screens.MediaCacheSingleton
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@UnstableApi
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val fetchPostsUseCase: FetchPostsUseCase,
    @ApplicationContext private val context: Context
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

    private var lastVisiblePostId: String? = null
    private var isLoadingMore = false

    private val _loadMoreVideos = mutableStateOf(false)
    val loadMoreVideos = _loadMoreVideos

    var cacheDataSourceFactory : CacheDataSource.Factory? = null
        private set

    init {
        fetchInitialPosts()
        createSimpleCache()
    }

    fun fetchInitialPosts() {
        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading
            val result = fetchPostsUseCase.fetchPosts()
            val posts = result.getOrNull() ?: emptyList()

            _videos.value = posts
            lastVisiblePostId = posts.lastOrNull()?.id

            _uiState.value = result.fold(
                onSuccess = { FeedUiState.Success(it) },
                onFailure = { FeedUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun fetchMorePosts() {
        if (isLoadingMore || lastVisiblePostId == null) return

        viewModelScope.launch {
            isLoadingMore = true
            _loadMoreVideos.value = true

            val result = fetchPostsUseCase.fetchPosts(num = 3, lastVisibleId = lastVisiblePostId)
            val newPosts = result.getOrNull() ?: emptyList()

            // Append to current list
            _videos.value = _videos.value + newPosts
            lastVisiblePostId = newPosts.lastOrNull()?.id

            _loadMoreVideos.value = false
            isLoadingMore = false
        }
    }


    @OptIn(UnstableApi::class)
    fun createSimpleCache(){
        val cache = MediaCacheSingleton.getInstance(context)
        val httpDataSourceFactory = DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)
        cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(DefaultDataSource.Factory(context, httpDataSourceFactory))
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

}