package com.example.tiktokandroid.auth.domain.usecases

import com.example.tiktokandroid.auth.domain.repositories.AuthRepository
import com.example.tiktokandroid.core.presentation.model.User
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        dob: String,
        username: String
    ): Result<User> {
        return repository.signup(email, dob, username)
    }
}