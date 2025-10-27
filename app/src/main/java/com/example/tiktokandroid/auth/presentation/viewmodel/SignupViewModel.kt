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
import com.example.tiktokandroid.auth.domain.usecases.CheckEmailUserCase
import com.example.tiktokandroid.auth.domain.usecases.CheckPhoneNumberUseCase
import com.example.tiktokandroid.auth.domain.usecases.SignupUseCase
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
class SignupViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signupUseCase: SignupUseCase,
    private val checkEmailUserCase: CheckEmailUserCase,
    private val checkPhoneNumberUseCase: CheckPhoneNumberUseCase,
    private val verifyOtpUseCase: VerifyOTPUseCase
) : ViewModel() {

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isPasswordValid = MutableStateFlow(false)
    val isPasswordValid: StateFlow<Boolean> = _isPasswordValid.asStateFlow()


    private val _userState = MutableStateFlow(SignupUiState())
    val userState: StateFlow<SignupUiState> = _userState.asStateFlow()

    private val _otpUiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val otpUiState = _otpUiState.asStateFlow()

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
     * Validate email address
     */
    fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) return false

        // Simple regex for email validation
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")

        return emailPattern.matches(email)
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


    /**
     * Create new user account
     */
    fun emailSignup() {

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = signupUseCase(
                _userState.value.email,
                _userState.value.password,
                _userState.value.dob,
                _userState.value.username
            )
            _uiState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = {
                    AuthUiState.Error(it.message ?: "Unknown error")

                }
            )
        }
    }

    fun resetUiState() {
        _uiState.value = AuthUiState.Idle
    }

    private fun isPasswordValid(password: String): Boolean {
        val hasMinLength = password.length >= 8
        val hasNumber = password.any { it.isDigit() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasSpecial = password.any { !it.isLetterOrDigit() }

        return hasMinLength && hasNumber && hasUpperCase && hasLowerCase && hasSpecial
    }

    /**
     * Update user state email
     */
    fun onEmailChange(newEmail: String) {
        _userState.value = _userState.value.copy(email = newEmail)
    }

    /**
     * Update user state password
     */
    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _isPasswordValid.value = isPasswordValid(newPassword)
        _userState.value = _userState.value.copy(password = newPassword)
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

    /**
     * Generates default username
     */
    fun generateUsername(): String {
        val emailPrefix =
            _userState.value.email.substringBefore("@").replace("[^A-Za-z0-9]".toRegex(), "")

        // Convert DOB string to format YYYYMMDD (assumes dob input is like "5 October, 1995" or "05-10-1995")
        val dobFormatted = _userState.value.dob
            .replace("[^0-9]".toRegex(), "") // remove non-numeric characters
            .padStart(8, '0') // ensure at least 8 digits

        // Generate a short random number to make it unique
        val randomSuffix = (100..999).random()

        val username = "${emailPrefix}_${dobFormatted}_$randomSuffix"

        return username.lowercase()
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


    /**
     * Check if phone number exists in fire store
     */
    fun checkPhoneNumber(number: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = checkPhoneNumberUseCase(number)
            _uiState.value = result.fold(
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

    fun verifyOtp(verificationId: String, otp: String) {
        viewModelScope.launch {
            _otpUiState.value = AuthUiState.Loading
            val result = verifyOtpUseCase(verificationId, otp)
            _otpUiState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }


}