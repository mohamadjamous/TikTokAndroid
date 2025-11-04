package com.example.tiktokandroid.feed.domain.usecases

import android.content.Context
import androidx.lifecycle.Observer
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.domain.repositories.FeedRepository
import com.example.tiktokandroid.feed.presentation.viewmodel.FeedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.manipulation.Ordering
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock


@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


}