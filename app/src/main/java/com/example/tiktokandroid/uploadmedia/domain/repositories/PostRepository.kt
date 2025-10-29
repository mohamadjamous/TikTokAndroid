package com.example.tiktokandroid.uploadmedia.domain.repositories

import android.net.Uri
import com.example.tiktokandroid.auth.data.datasource.AuthRemoteDataSource
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.uploadmedia.data.datasource.PostRemoteDataSource
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val dataSource: PostRemoteDataSource
) {

    suspend fun uploadPost(post: Post, videoUri: Uri): Result<Post> {
        return dataSource.uploadPost(post, videoUri)
    }

}