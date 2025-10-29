package com.example.tiktokandroid.core.sharedpreferences

import android.content.Context
import com.example.tiktokandroid.core.presentation.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Save user data
    fun saveUser(
        uid: String,
        username: String,
        dob: String,
        phone: String,
        email: String
    ) {
        with(prefs.edit()) {
            putString("uid", uid)
            putString("username", username)
            putString("dob", dob)
            putString("phone", phone)
            putString("email", email)
            commit()
        }
    }

    // Retrieve stored user
    fun getUser(): User? {
        val uid = prefs.getString("uid", null) ?: return null
        val username = prefs.getString("username", "") ?: ""
        val dob = prefs.getString("dob", "") ?: ""
        val phone = prefs.getString("phone", "") ?: ""
        val email = prefs.getString("email", "") ?: ""
        return User(
            id = uid,
            username = username,
            dob = dob,
            phone = phone,
            email = email
        )
    }

    // Clear user data (for logout)
    fun clearUser() {
        prefs.edit().clear().commit()
    }
}
