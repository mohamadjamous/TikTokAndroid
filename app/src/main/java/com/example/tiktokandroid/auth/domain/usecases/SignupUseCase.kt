package com.example.tiktokandroid.auth.domain.usecases

import com.example.tiktokandroid.auth.domain.repositories.AuthRepository
import com.example.tiktokandroid.core.presentation.model.User
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        dob: String,
        username: String
    ): Result<User> {
        return repository.emailSignup(email, password,dob, username)
    }

    suspend operator fun invoke(
        phoneNumber: String,
        dob: String,
        username: String
    ): Result<User> {
        return repository.phoneNumberSignup(phoneNumber, dob, username)
    }
}