package com.example.tiktokandroid.auth.domain.usecases

import com.example.tiktokandroid.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class CheckPhoneNumberUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        phone: String
    ): Result<Boolean> {
        return repository.checkPhoneNumber(phone)
    }
}