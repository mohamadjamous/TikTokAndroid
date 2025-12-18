package com.example.tiktokandroid.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {



    fun logout(){
        userPreferences.clearUser()
    }


}