package com.example.tiktokandroid.feed.domain.usecases

import com.example.tiktokandroid.core.presentation.model.Post
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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

        assertTrue(result.isSuccess)
        assertEquals(fakePosts, result.getOrNull())
    }

    @Test
    fun `fetchPosts returns failure when repository fails`() = runTest {
        val error = Exception("Network error")
        fakeRepository.setFailure(error)

        val result = fetchPostsUseCase.fetchPosts(3)

        assertTrue(result.isFailure)
        assertEquals(error.message, result.exceptionOrNull()?.message)
    }
}