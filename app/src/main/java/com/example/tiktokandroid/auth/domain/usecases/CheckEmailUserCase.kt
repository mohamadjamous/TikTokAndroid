package com.example.tiktokandroid.auth.domain.usecases

import com.example.tiktokandroid.auth.domain.repositories.AuthRepository
import com.example.tiktokandroid.core.presentation.model.User
import javax.inject.Inject

class CheckEmailUserCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String
    ): Result<Boolean> {
        return repository.checkEmail(email)
    }
}