package com.example.tiktokandroid.profile.domain.usecases

import com.example.tiktokandroid.auth.domain.repositories.UserRepository
import com.example.tiktokandroid.core.presentation.model.Post
import javax.inject.Inject

class FetchUserSavedPostsUseCase @Inject
 constructor(
     private val userRepository: UserRepository
 ){

    suspend operator fun invoke(
        uid : String
    ): Result<List<Post>> {
        val posts = userRepository.fetchUserSavedPosts(uid)

        return posts.mapCatching { list ->
            list.sortedByDescending { it.createdAt }
        }
    }


}