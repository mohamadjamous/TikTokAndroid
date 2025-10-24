package com.example.tiktokandroid.auth.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktokandroid.auth.data.model.AuthUiState
import com.example.tiktokandroid.auth.domain.usecases.CheckEmailUserCase
import com.example.tiktokandroid.auth.domain.usecases.SignupUseCase
import com.example.tiktokandroid.core.presentation.model.Country
import com.example.tiktokandroid.core.presentation.model.CountryJsonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.InputStreamReader


@HiltViewModel
class SignupViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signupUseCase: SignupUseCase,
    private val checkEmailUserCase: CheckEmailUserCase
) : ViewModel() {

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

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
     * Check if email does not exist already in firebase firestore
     */
    fun checkEmail(email : String){

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
    fun emailSignup(email: String, password: String, username: String, dob: String) {

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = signupUseCase(email, dob, username)
            _uiState.value = result.fold(
                onSuccess = { AuthUiState.Success(it) },
                onFailure = { AuthUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

}