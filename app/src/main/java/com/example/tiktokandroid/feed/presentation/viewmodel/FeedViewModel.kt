package com.example.tiktokandroid.feed.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import com.example.tiktokandroid.feed.data.model.FeedUiState
import com.example.tiktokandroid.feed.domain.usecases.FetchPostsUseCase
import com.example.tiktokandroid.feed.domain.usecases.UpdateLikeStateUseCase
import com.example.tiktokandroid.feed.domain.usecases.UpdateSavedStateUseCase
import com.example.tiktokandroid.feed.domain.usecases.VideoStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

@UnstableApi
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val fetchPostsUseCase: FetchPostsUseCase,
    private val updateLikeStateUseCase: UpdateLikeStateUseCase,
    private val updateSavedStateUseCase: UpdateSavedStateUseCase,
    private val userSharedPreferences: UserPreferences,
    private val videoStatsUseCase: VideoStatsUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _videos = MutableStateFlow<List<Post>>(emptyList())
    val videos get() = _videos

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Idle)
    val uiState get() = _uiState

    private val statsCache = mutableMapOf<String, Pair<Boolean, Boolean>>()


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

    var cacheDataSourceFactory: CacheDataSource.Factory? = null
        private set

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val _likedMap = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val likedMap = _likedMap.asStateFlow()

    private val _savedMap = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val savedMap = _savedMap.asStateFlow()


    private val newCommentCallbacks = mutableMapOf<String, () -> Unit>()


    init {
        fetchStoredUser()
        fetchInitialPosts()
        createSimpleCache()
    }

    private fun fetchInitialPosts() {

        if (isLoadingMore) return
        isLoadingMore = true

        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading

            // Try to fetch remote data
            val result = fetchPostsUseCase.invoke(
                num = 5,
                lastVisibleId = null
            )

            result
                .onSuccess { posts ->

                    // Update last Id
                    lastVisiblePostId = posts.lastOrNull()?.id

                    _uiState.value = FeedUiState.Success(posts)
                    _videos.value += posts
                }
                .onFailure { error ->
                    _uiState.value = FeedUiState.Error(
                        error.message ?: "Unknown error"
                    )
                }

            isLoadingMore = false
        }

    }


    fun fetchMorePosts() {
        if (isLoadingMore || lastVisiblePostId == null) return

        viewModelScope.launch {
            isLoadingMore = true
            _loadMoreVideos.value = true

            val result = fetchPostsUseCase.fetchLocalPosts(num = 5, lastVisibleId = lastVisiblePostId)

            result.onSuccess { newPosts ->

                // Update last Id
                lastVisiblePostId = newPosts.lastOrNull()?.id

                _videos.value = _videos.value + newPosts
            }

            _loadMoreVideos.value = false
            isLoadingMore = false
        }
    }


    fun createSimpleCache() {
        val cache = MediaCacheSingleton.getInstance(context)
        val httpDataSourceFactory =
            DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)
        cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(DefaultDataSource.Factory(context, httpDataSourceFactory))
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    /**
     * Update like status of a video
     */
    fun updateLikeState(videoId: String, liked: Boolean) {
        viewModelScope.launch {
            updateLikeStateUseCase.updateLikeState(
                videoId, liked
            )
        }
    }

    @UnstableApi
    object MediaCacheSingleton {
        private var simpleCache: SimpleCache? = null

        fun getInstance(context: Context): SimpleCache {
            if (simpleCache == null) {
                val cacheDir = File(context.cacheDir, "media_cache")
                val evictor = LeastRecentlyUsedCacheEvictor(50L * 1024L * 1024L) // 50 MB
                val databaseProvider = StandaloneDatabaseProvider(context)
                simpleCache = SimpleCache(cacheDir, evictor, databaseProvider)
            }
            return simpleCache!!
        }
    }

    fun fetchStoredUser() {
        _currentUser.value = userSharedPreferences.getUser()
    }


    /**
     * Update video saved state
     */
    fun updateSavedState(id: String, saved: Boolean) {
        viewModelScope.launch {
            updateSavedStateUseCase.updateSavedState(
                id, saved
            )
        }
    }


    /**
     * Fetch video liked and saved state by user
     */
    fun fetchVideoStats(videoId: String) {
        viewModelScope.launch {
            val user = userSharedPreferences.getUser() ?: return@launch

            val liked = videoStatsUseCase.getLikedState(videoId, user.id)
            val saved = videoStatsUseCase.getSavedState(videoId, user.id)

            _likedMap.update { it + (videoId to liked) }
            _savedMap.update { it + (videoId to saved) }
        }
    }


    fun incrementCommentCount(videoId: String) {

    }


}