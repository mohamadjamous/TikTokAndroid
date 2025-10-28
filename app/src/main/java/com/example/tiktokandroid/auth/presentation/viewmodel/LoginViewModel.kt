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
import com.example.tiktokandroid.auth.domain.usecases.CheckPhoneNumberUseCase
import com.example.tiktokandroid.auth.domain.usecases.PhoneLoginUseCase
import com.example.tiktokandroid.auth.domain.usecases.SendOTPCodeUseCase
import com.example.tiktokandroid.auth.domain.usecases.VerifyOTPUseCase
import com.example.tiktokandroid.core.presentation.model.Country
import com.example.tiktokandroid.core.presentation.model.CountryJsonResponse
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

    fun isPhoneNumberValid(number: String): Boolean {
        // Regex to match + followed by 1-3 digits country code and then 4-14 digits
        val regex = Regex("""^\+\d{1,3}\d{4,14}$""")
        return regex.matches(number)
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
}