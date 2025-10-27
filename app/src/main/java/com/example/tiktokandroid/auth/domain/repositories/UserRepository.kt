package com.example.tiktokandroid.auth.domain.repositories

import android.content.Context
import com.example.tiktokandroid.core.presentation.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getStoredUser(): User? {
        val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val uid = sharedPref.getString("uid", null) ?: return null
        val email = sharedPref.getString("email", "") ?: ""
        val username = sharedPref.getString("username", "") ?: ""
        val dob = sharedPref.getLong("dob", 0L)
        val phone = sharedPref.getString("phone", "") ?: ""
        return User(uid, username, dob.toString(), email, phone = phone)
    }
}
