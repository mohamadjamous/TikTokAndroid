package com.example.tiktokandroid.auth.presentation.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktokandroid.auth.data.model.AuthUiState
import com.example.tiktokandroid.auth.data.model.SignupUiState
import com.example.tiktokandroid.auth.domain.usecases.CheckPhoneNumberUseCase
import com.example.tiktokandroid.auth.domain.usecases.SendOTPCodeUseCase
import com.example.tiktokandroid.auth.domain.usecases.VerifyOTPUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PhoneNumberViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOTPUseCase,
    private val sendOTPCodeUseCase: SendOTPCodeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _userState = MutableStateFlow(SignupUiState())
    val userState: StateFlow<SignupUiState> = _userState.asStateFlow()

    var verificationId by mutableStateOf<String?>(null)
        private set


    init {
        sendOtpCode(_userState.value.phoneNumber)
    }


    fun resetUiState() {
        _uiState.value = AuthUiState.Idle
    }



    /**
     * Function to restart the app after signup
     * or login
     */
    fun restartApp(context: Context) {
        val intent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)
            ?.apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
        context.startActivity(intent)
        if (context is Activity) {
            context.finish()
        }
        Runtime.getRuntime().exit(0) // optional, ensures process restart
    }





    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = verificationId?.let { verifyOtpUseCase(it, otp) }
            _uiState.value = result?.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Unknown error") }
            )!!
        }
    }

    fun sendOtpCode(number: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = sendOTPCodeUseCase(number)
            _uiState.value = result.fold(
                onSuccess = {
                    verificationId = it
                    AuthUiState.Success(it)
                },
                onFailure = { AuthUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    /**
     * Update user state password
     */
    fun onDobChange(newDob: String) {
        _userState.value = _userState.value.copy(dob = newDob)
    }

    /**
     * Update user state username
     */
    fun onUsernameChange(newUsername: String) {
        _userState.value = _userState.value.copy(username = newUsername)
    }

    /**
     * Update user state phone number
     */
    fun onPhoneNumberChange(newNumber: String) {
        _userState.value = _userState.value.copy(phoneNumber = newNumber)
    }


}