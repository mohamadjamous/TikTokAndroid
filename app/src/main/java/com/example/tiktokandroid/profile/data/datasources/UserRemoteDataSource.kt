package com.example.tiktokandroid.profile.data.datasources

import android.content.Context
import com.example.tiktokandroid.core.presentation.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    @ApplicationContext private val context: Context
) {

    suspend fun fetchUserPosts(uid: String): Result<List<Post>> {
        return try {
            val snapshot = firestore
                .collection("posts")
                .whereEqualTo("userId", uid)
                .get()
                .await()

            val posts = snapshot.toObjects(Post::class.java)
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchSavedPosts(uid: String): Result<List<Post>> {
        return try {

            // Get all saved video IDs for the user
            val savedSnapshot = firestore
                .collection("saves")
                .document(uid)
                .collection("videos")
                .get()
                .await()

            val savedVideoIds = savedSnapshot.documents.map { it.id }

            if (savedVideoIds.isEmpty()) {
                return Result.success(emptyList())
            }

            // Fetch posts with these IDs
            // Firestore allows querying with 'whereIn' for up to 10 IDs at a time
            val posts = mutableListOf<Post>()
            val chunkedIds = savedVideoIds.chunked(10)

            for (chunk in chunkedIds) {
                val postSnapshot = firestore
                    .collection("posts")
                    .whereIn("id", chunk)
                    .get()
                    .await()

                posts.addAll(postSnapshot.toObjects(Post::class.java))
            }

            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}