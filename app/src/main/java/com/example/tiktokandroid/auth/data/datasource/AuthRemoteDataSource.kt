package com.example.tiktokandroid.auth.data.datasource

import android.content.Context
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.feed.presentation.view.MainActivity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseException
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume

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
            Result.success(User(id = firebaseUser.uid, email = email, username = username, dob = dob, phone = ""))
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

    suspend fun checkPhoneNumber(
        number: String
    ): Result<Boolean> {

        println("NumberBeingChecked: $number")
        return try {
            val querySnapshot = firestore
                .collection("users")
                .whereEqualTo("phone", number)
                .get()
                .await()

            val exists = !querySnapshot.isEmpty
            Result.success(exists)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyOtp(verificationId: String, otp: String): Result<Boolean> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            if (authResult.user != null) {
                Result.success(true)
            } else {
                Result.failure(Throwable("OTP verification failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendOtpCode(phoneNumber: String): Result<String> {
        return suspendCancellableCoroutine { continuation ->

            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Instant verification on some devices (auto verify)
                    continuation.resume(Result.success("AUTO_VERIFIED"))
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    continuation.resume(Result.failure(e))
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    continuation.resume(Result.success(verificationId))
                }
            }

            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(MainActivity.instance) // 👈 must be an Activity context
                .setCallbacks(callbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    suspend fun phoneNumberSignup(
        phoneNumber: String,
        dob: String,
        username: String
    ): Result<User> {
        return try {
            val firebaseUser = firebaseAuth.currentUser ?: throw Exception("User not authenticated")

            // Convert DOB string to Timestamp
            val formatter = SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH)
            val date = formatter.parse(dob) ?: throw Exception("Invalid date format")
            val dobTimestamp = Timestamp(date)

            // Save user info in Firestore
            val userMap = mapOf(
                "uid" to firebaseUser.uid,
                "phone" to phoneNumber,
                "username" to username,
                "email" to "",
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
                putString("phone", phoneNumber)
                putString("username", username)
                putLong("dob", dobTimestamp.seconds)
                apply()
            }

            Result.success(
                User(
                    id = firebaseUser.uid,
                    phone = phoneNumber,
                    username = username,
                    email = "",
                    dob = dob
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun phoneLogin(phoneNumber: String): Result<User> {
        return try {
            val firebaseUser = firebaseAuth.currentUser
                ?: throw Exception("User not authenticated")

            val userDoc = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            if (!userDoc.exists()) {
                throw Exception("User not found in Firestore")
            }

            val username = userDoc.getString("username") ?: ""
            val dobTimestamp = userDoc.getTimestamp("dob")
            val dob = dobTimestamp?.toDate()?.let {
                SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH).format(it)
            } ?: ""

            val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("uid", firebaseUser.uid)
                putString("phone", phoneNumber)
                putString("username", username)
                putString("dob", dob)
                putString("email", "")
                apply() // or commit()
            }

            Result.success(
                User(
                    id = firebaseUser.uid,
                    phone = phoneNumber,
                    username = username,
                    email = "",
                    dob = dob
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun emailLogin(email: String, password: String): Result<User> {

        return try {
            // Sign in with Firebase Auth
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw Exception("User not found after login")

            // Fetch user info from Firestore
            val userDoc = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            if (!userDoc.exists()) {
                throw Exception("User not found in Firestore")
            }

            val username = userDoc.getString("username") ?: ""
            val dobTimestamp = userDoc.getTimestamp("dob")
            val dob = dobTimestamp?.toDate()?.let {
                SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH).format(it)
            } ?: ""

            // Save to SharedPreferences
            val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("uid", firebaseUser.uid)
                putString("email", email)
                putString("username", username)
                putString("dob", dob)
                putString("phone", "")
                apply()
            }

            // Return success result
            Result.success(
                User(
                    id = firebaseUser.uid,
                    email = email,
                    username = username,
                    dob = dob,
                    phone = ""
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}