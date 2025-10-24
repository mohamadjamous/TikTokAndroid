package com.example.tiktokandroid.auth.data.datasource

import com.example.tiktokandroid.core.presentation.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun signup(
        email: String,
        dob: String,
        username: String
    ): Result<User> {

        return Result.failure(
            exception = Throwable(message = "error")
        )
    }

    suspend fun checkEmail(
        email: String
    ): Result<Boolean> {

        return try {
            val querySnapshot = firestore
                .collection("users")
                .whereEqualTo("email", email)
                .get()
                .await()

            val exists = !querySnapshot.isEmpty
            Result.success(exists)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}