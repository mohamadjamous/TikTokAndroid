package com.example.tiktokandroid.uploadmedia.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val userSharedPreferences: UserPreferences
) : ViewModel() {


    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser


    init {
        fetchStoredUser()
    }

    fun fetchStoredUser() {
        _currentUser.value = userSharedPreferences.getUser()
    }

    @SuppressLint("Recycle")
    fun loadDeviceVideos(context: Context): List<Uri> {
        val videos = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Video.Media._ID)
        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"
        val query = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                videos.add(contentUri)
            }
        }
        return videos
    }

}