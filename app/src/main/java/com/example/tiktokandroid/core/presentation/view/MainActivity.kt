package com.example.tiktokandroid.core.presentation.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.tiktokandroid.core.service.VideoPrefetchService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        lateinit var instance: MainActivity
            private set
    }
    lateinit var context : Context
    private var prefetchService: VideoPrefetchService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        instance = this
        context = this

        // Start service once
        val intent = Intent(this, VideoPrefetchService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE)


        setContent {
            RootView(onPrefetch = { index, list ->
                prefetchService?.prefetch(index, list)
            })
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val b = binder as VideoPrefetchService.LocalBinder
            prefetchService = b.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            prefetchService = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
        prefetchService = null
    }
}