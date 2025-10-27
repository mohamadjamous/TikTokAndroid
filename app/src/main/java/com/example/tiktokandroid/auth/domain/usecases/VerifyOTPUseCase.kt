package com.example.tiktokandroid.auth.domain.usecases

import com.example.tiktokandroid.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class VerifyOTPUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(verificationId: String, otp: String): Result<Boolean> {
        if (otp.length != 6) return Result.failure(Throwable("OTP must be 6 digits"))
        return repository.verifyOtp(verificationId, otp)
    }
}