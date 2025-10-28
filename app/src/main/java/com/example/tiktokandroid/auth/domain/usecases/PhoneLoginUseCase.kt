package com.example.tiktokandroid.auth.domain.usecases

import com.example.tiktokandroid.auth.domain.repositories.AuthRepository
import com.example.tiktokandroid.core.presentation.model.User
import javax.inject.Inject

class PhoneLoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        number: String
    ): Result<User> {
        return repository.phoneLogin(number)
    }
}