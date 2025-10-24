package com.example.tiktokandroid.auth.domain.repositories

import com.example.tiktokandroid.auth.data.datasource.AuthRemoteDataSource
import com.example.tiktokandroid.core.presentation.model.User
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val dataSource: AuthRemoteDataSource
) {

    suspend fun signup(
        email: String,
        dob: String,
        username: String
    ): Result<User> {
        return dataSource.signup(
            email,
            dob,
            username
        )
    }

    suspend fun checkEmail(
        email: String
    ): Result<Boolean> {
        return dataSource.checkEmail(
            email)
    }
}