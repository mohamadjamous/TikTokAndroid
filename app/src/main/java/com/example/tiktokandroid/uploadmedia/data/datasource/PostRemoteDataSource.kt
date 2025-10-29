package com.example.tiktokandroid.uploadmedia.data.datasource

import android.content.Context
import android.net.Uri
import com.example.tiktokandroid.core.presentation.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import java.util.UUID

class PostRemoteDataSource
 @Inject constructor(
private val firestore: FirebaseFirestore,
private val firebaseStorage: FirebaseStorage,
@ApplicationContext private val context: Context

) {

    suspend fun uploadPost(post: Post, videoUri: Uri): Result<Post> {
        return try {

            // Upload video to Firebase Storage
            val storageRef = firebaseStorage.reference
            val videoRef = storageRef.child("videos/${UUID.randomUUID()}.mp4")

            videoRef.putFile(videoUri).await()

            // Get the download URL as a String
            val downloadUrl = videoRef.downloadUrl.await().toString()

            // Create new post object with the URL
            val updatedPost = post.copy(
                id = UUID.randomUUID().toString(),
                videoUrl = downloadUrl
            )

            // Save post data to Firestore
            val firestore = firestore
            firestore.collection("posts")
                .document(updatedPost.id)
                .set(updatedPost)
                .await()

            // Return success
            Result.success(updatedPost)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}