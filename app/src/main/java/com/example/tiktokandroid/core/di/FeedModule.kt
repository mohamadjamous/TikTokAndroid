package com.example.tiktokandroid.core.di

import com.example.tiktokandroid.feed.domain.interfaces.IFeedRepository
import com.example.tiktokandroid.feed.domain.repositories.FeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedModule {


    @Binds
    @Singleton
    abstract fun bindFeedRepository(
        impl: FeedRepository
    ): IFeedRepository
}