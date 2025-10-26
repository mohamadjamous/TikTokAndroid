package com.example.tiktokandroid.auth.data.datasource

import android.content.Context
import com.example.tiktokandroid.core.presentation.model.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) {

    suspend fun emailSignup(
        email: String,
        password: String,
        dob: String,
        username: String
    ): Result<User> {
        return try {

            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw Exception("User creation failed")

            // Convert DOB string to Timestamp
            val formatter = SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH)
            val date = formatter.parse(dob) ?: throw Exception("Invalid date format")
            val dobTimestamp = Timestamp(date)

            // Save user info in Firestore
            val userMap = mapOf(
                "uid" to firebaseUser.uid,
                "email" to email,
                "username" to username,
                "dob" to dobTimestamp
            )

            firestore.collection("users")
                .document(firebaseUser.uid)
                .set(userMap)
                .await()

            // Save locally in SharedPreferences
            val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("uid", firebaseUser.uid)
                putString("email", email)
                putString("username", username)
                putLong("dob", dobTimestamp.seconds) // store as seconds
                apply()
            }

            // Return success
            Result.success(User(id = firebaseUser.uid, email = email, username = username, dob = dob))
        } catch (e: Exception) {
            Result.failure(e)
        }
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