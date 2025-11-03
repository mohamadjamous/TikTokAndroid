package com.example.tiktokandroid.profile.domain.repositories

import com.example.tiktokandroid.auth.data.datasource.AuthRemoteDataSource
import javax.inject.Inject


class ProfileRepository @Inject constructor(
    private val dataSource: AuthRemoteDataSource
) {
}