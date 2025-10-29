package com.example.tiktokandroid.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Common @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /**
     * Function to restart the app after signup
     * or login
     */
    companion object {
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
    }

    fun isPhoneNumberValid(number: String): Boolean {
        // Regex to match + followed by 1-3 digits country code and then 4-14 digits
        val regex = Regex("""^\+\d{1,3}\d{4,14}$""")
        return regex.matches(number)
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

}