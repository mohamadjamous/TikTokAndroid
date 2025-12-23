package com.example.tiktokandroid.auth.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userSharedPreferences: UserPreferences,
    private val userPreferences: UserPreferences
) : ViewModel() {


    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser


    init {
        fetchStoredUser()
    }

    fun logout(){
        userPreferences.clearUser()
    }

    fun fetchStoredUser() {
        _currentUser.value = userSharedPreferences.getUser()
    }


}