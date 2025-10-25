package com.example.tiktokandroid.auth.data.model

enum class PasswordStrength { Weak, Medium, Strong }

object PasswordValidator {
    private const val MIN_LENGTH = 8

    fun isMinLength(password: String) = password.length >= MIN_LENGTH
    fun hasDigit(password: String) = password.any { it.isDigit() }
    fun hasLower(password: String) = password.any { it.isLowerCase() }
    fun hasUpper(password: String) = password.any { it.isUpperCase() }
    fun hasSpecial(password: String) = password.any { !it.isLetterOrDigit() }

    /** return true if password meets a reasonable rule set */
    fun isPasswordValid(password: String): Boolean {
        return isMinLength(password)
                && hasDigit(password)
                && hasLower(password)
                && hasUpper(password)
                && hasSpecial(password)
    }

    /** simple strength heuristic */
    fun passwordStrength(password: String): PasswordStrength {
        var score = 0
        if (isMinLength(password)) score++
        if (hasDigit(password)) score++
        if (hasLower(password) && hasUpper(password)) score++
        if (hasSpecial(password)) score++

        return when (score) {
            in 0..1 -> PasswordStrength.Weak
            2 -> PasswordStrength.Medium
            else -> PasswordStrength.Strong
        }
    }
}
