package com.example.tiktokandroid.uploadmedia.domain.usecases

import android.net.Uri
import com.example.tiktokandroid.auth.domain.repositories.AuthRepository
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.uploadmedia.domain.repositories.PostRepository
import javax.inject.Inject

class PostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        post: Post,
        videoUri: Uri
    ): Result<Post> {
        return repository.uploadPost(post, videoUri)
    }

}