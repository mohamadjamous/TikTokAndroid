package com.example.tiktokandroid.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@HiltViewModel
class LanguageViewModel @Inject constructor() : ViewModel() {


    private val _languages = MutableStateFlow<List<String>>(emptyList())
    val languages: StateFlow<List<String>> get() = _languages



    init {
        initLanguages()
    }

    private fun initLanguages() {
        _languages.value += "Arabic"
        _languages.value += "English"
    }
}