package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.core.presentation.model.Post
import com.google.common.truth.ExpectFailure.assertThat
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class FetchPostsUseCaseTest {


    private lateinit var fakeRepository: FakeFeedRepository
    private lateinit var fetchPostsUseCase: FetchPostsUseCase


    @Before
    fun setUp(){
        fakeRepository = FakeFeedRepository()
        fetchPostsUseCase = FetchPostsUseCase(fakeRepository)
    }

    @Test
    fun `get posts list, correct post list return`() = runBlocking{

        val fakePosts = listOf(
            Post(id = "1", username = "Alice", description = "First post"),
            Post(id = "2", username = "Bob", description = "Second post")
        )

        fakeRepository.setFakePosts(fakePosts)
        val result = fetchPostsUseCase.fetchPosts(2)

        assertThat(result.getOrNull()).isNotNull()
    }

    @Test
    fun `fetchPosts returns failure when repository fails`() = runTest {
        val error = Exception("Network error")
        fakeRepository.setFailure(error)

        val result = fetchPostsUseCase.fetchPosts(3)

        assertThat(result.getOrNull()).isNull()
    }
}