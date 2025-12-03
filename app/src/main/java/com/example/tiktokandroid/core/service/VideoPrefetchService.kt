package com.example.tiktokandroid.core.service

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.domain.usecases.FetchPostsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VideoPrefetchService : LifecycleService() {


    @Inject lateinit var fetchPostsUseCase : FetchPostsUseCase
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    inner class LocalBinder : Binder(){
        fun getService() : VideoPrefetchService = this@VideoPrefetchService
    }

    private val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("PrefetchService", "Prefetch Service Started")
    }

    fun prefetch(index: Int, currentList: List<Post>){

        Log.d("PrefetchService", "Prefetch Called")


        // Don't prefetch if near the end or list too small
        if (index < 0 || currentList.isEmpty()) return

        val remaining = currentList.size - index


        // We still have many local items, no need to fetch
        if (remaining > 5) return

        val lastId = currentList.lastOrNull()?.id ?: return

        serviceScope.launch {
            val result = fetchPostsUseCase.invoke(num = 5, lastVisibleId = lastId)

            result.onSuccess{ posts ->

                // store in cache only
                fetchPostsUseCase.cachePosts(posts)
                Log.d("PrefetchService", "Prefetch Service cached ${posts.size} posts")

            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}