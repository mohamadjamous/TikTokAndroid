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
import com.example.tiktokandroid.auth.domain.usecases.CheckEmailUserCase
import com.example.tiktokandroid.auth.domain.usecases.CheckPhoneNumberUseCase
import com.example.tiktokandroid.auth.domain.usecases.EmailLoginUseCase
import com.example.tiktokandroid.auth.domain.usecases.PhoneLoginUseCase
import com.example.tiktokandroid.auth.domain.usecases.SendOTPCodeUseCase
import com.example.tiktokandroid.auth.domain.usecases.VerifyOTPUseCase
import com.example.tiktokandroid.core.presentation.model.Country
import com.example.tiktokandroid.core.presentation.model.CountryJsonResponse
import com.example.tiktokandroid.utils.Common
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.InputStreamReader

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val checkPhoneNumberUseCase: CheckPhoneNumberUseCase,
    private val verifyOtpUseCase: VerifyOTPUseCase,
    private val sendOTPCodeUseCase: SendOTPCodeUseCase,
    private val phoneLoginUseCase: PhoneLoginUseCase,
    private val checkEmailUserCase: CheckEmailUserCase,
    private val emailLoginUseCase: EmailLoginUseCase,
    private val common: Common
) : ViewModel() {

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _phoneState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val phoneState = _phoneState.asStateFlow()

    private val _otpState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val otpState = _otpState.asStateFlow()

    private val _sendCodeState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val sendCodeState = _sendCodeState.asStateFlow()

    private val _loginState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val loginState: StateFlow<AuthUiState> get() = _loginState

    var verificationId by mutableStateOf<String?>(null)
        private set


    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState : StateFlow<AuthUiState> get() = _uiState

    init {
        loadCountries()
    }


    /**
     * Load all countries on IO dispatcher
     */
    private fun loadCountries() {

        viewModelScope.launch(context = Dispatchers.IO) {

            val countriesList = parseCountriesJson()

            val mappedList = countriesList.map { country ->
                Country(
                    name = country.country,
                    code = country.code,
                    iso = country.iso,
                )
            }

            _countries.value = mappedList


        }
    }

    /**
     * Parse countries from json file
     */
    private fun parseCountriesJson(): List<CountryJsonResponse> {
        val inputStream = context.assets.open("countries.json")
        val jsonString = InputStreamReader(inputStream).readText()
        return Json.decodeFromString(jsonString)
    }

    /**
     * Check if phone number exists in fire store
     */
    fun checkPhoneNumber(number: String) {
        viewModelScope.launch {
            _phoneState.value = AuthUiState.Loading
            val result = checkPhoneNumberUseCase(number)
            _phoneState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }


    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            _otpState.value = AuthUiState.Loading
            val result = verificationId?.let { verifyOtpUseCase(it, otp) }
            _otpState.value = result?.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Unknown error") }
            )!!
        }
    }

    fun sendOtpCode(number: String) {
        viewModelScope.launch {
            _sendCodeState.value = AuthUiState.Loading
            val result = sendOTPCodeUseCase(number)
            _sendCodeState.value = result.fold(
                onSuccess = {
                    verificationId = it
                    AuthUiState.Success(it)
                },
                onFailure = { AuthUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun resetUiState() {
        _phoneState.value = AuthUiState.Idle
        _otpState.value = AuthUiState.Idle
        _sendCodeState.value = AuthUiState.Idle
    }



    fun phoneLogin(number: String) {

        println("PhoneNumberPassed: $number")
        viewModelScope.launch {
            _loginState.value = AuthUiState.Loading
            val result = phoneLoginUseCase(number)
            _loginState.value = result.fold(
                onSuccess = {
                    AuthUiState.Success(it)
                },
                onFailure = { AuthUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }


    /**
     * Check if email exists already in firebase firestore
     */
    fun checkEmail(email: String) {

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = checkEmailUserCase(email)
            _uiState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun emailLogin(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthUiState.Loading
            val result = emailLoginUseCase(email, password)
            _loginState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun isPhoneNumberValid(number: String) = common.isPhoneNumberValid(number)
    fun validateEmail(email: String) = common.validateEmail(email)


}