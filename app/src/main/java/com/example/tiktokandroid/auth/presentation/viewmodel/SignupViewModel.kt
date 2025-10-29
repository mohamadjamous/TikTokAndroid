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
import com.example.tiktokandroid.auth.domain.usecases.SendOTPCodeUseCase
import com.example.tiktokandroid.auth.domain.usecases.SignupUseCase
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
class SignupViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signupUseCase: SignupUseCase,
    private val checkEmailUserCase: CheckEmailUserCase,
    private val checkPhoneNumberUseCase: CheckPhoneNumberUseCase,
    private val verifyOtpUseCase: VerifyOTPUseCase,
    private val sendOTPCodeUseCase: SendOTPCodeUseCase,
    private val common: Common
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

    private val _phoneState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val phoneState = _phoneState.asStateFlow()

    private val _otpState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val otpState = _otpState.asStateFlow()

    private val _sendCodeState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val sendCodeState = _sendCodeState.asStateFlow()

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
        _phoneState.value = AuthUiState.Idle
        _otpState.value = AuthUiState.Idle
        _sendCodeState.value = AuthUiState.Idle
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

    fun phoneNumberSignup() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = signupUseCase(
                _userState.value.phoneNumber,
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

    fun isPhoneNumberValid(number: String) = common.isPhoneNumberValid(number)
    fun validateEmail(email: String) = common.validateEmail(email)


}